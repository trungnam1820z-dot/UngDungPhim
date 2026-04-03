package com.udxp.service;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.*;
import com.udxp.mapper.MovieMapper;
import com.udxp.repository.*;
import com.udxp.specification.MovieFilter;
import com.udxp.specification.MovieSpecification;
import com.udxp.util.UtilExcel;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.udxp.util.UtilExcel.isRowEmpty;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CountryRepository countryRepository;
    private final DirectorRepository directorRepository;
    private final CategoryRepository categoryRepository;
    private final MovieSearchRepository movieSearchRepository;
    private static final int BATCH_SIZE = 100;
    @Override
    public MovieResponse createMovie(MovieCreateRequest request) {
        if (movieRepository.existsMovieByTitle(request.getTitle())){
            log.error("Movie with title: {}", request.getTitle() + "already exists");
            throw new EntityExistsException("Movie with title " + request.getTitle() + " already exists");
        }
        Movie movie = movieMapper.toMovie(request);
        log.info("Movie with title: {} has been created", request.getTitle());
        // map country
        Country country = countryRepository.findById((long) Math.toIntExact(request.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found"));
        movie.setCountry(country);

        // map directors
        List<Director> directors = directorRepository.findAllById(request.getDirectorIds());
        movie.setDirectors(directors);

        // map categories
        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        movie.setCategories(categories);

        Movie saved = movieRepository.save(movie);
        return movieMapper.toResponse(saved);
    }
    @Override
    @Cacheable(value = "movies", key = "#id")
    public MovieResponse getMovieById(Long id) {
        return movieMapper.toResponse(movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found")));
    }
    @Override
    @Cacheable(value = "movies")
    public Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable) {
        Specification<Movie> spec = Specification
                .where(MovieSpecification.hasTitle(filter.getTitle()))
                .and(MovieSpecification.hasReleaseDate(filter.getReleaseDate()))
                .and(MovieSpecification.hasDirectorName(filter.getDirectorName()))
                .and(MovieSpecification.hasCategoryName(filter.getCategoryName()))
                .and(MovieSpecification.hasCountryName(filter.getCountryName()));
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("title").ascending()
        );
        Page<Movie> moviePage = movieRepository.findAll(spec, sortedPageable);
        return moviePage.map(movieMapper::toResponse);
    }

    @Override
    @CacheEvict(value = "movies", key = "#id")
    public MovieResponse updateMovie(Long id, MovieCreateRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieMapper.updateMovie(movie, request);
        log.info("Movie with id: {} has been updated", id);
        return movieMapper.toResponse(movieRepository.save(movie));
    }
    @Override
    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
        log.info("Movie with id: {} was deleted", id);
    }
    @Override
    public List<MovieDocument> searchAdvanced(String keyword, Integer releaseDate, String category) {
        return movieSearchRepository.searchAdvanced(keyword, releaseDate, category);
    }
    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream exportMovieToExcel(List<Movie> movies) throws IOException {
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Movies");
            // header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue("Category");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("ReleaseDate");
            headerRow.createCell(4).setCellValue("Director");
            headerRow.createCell(5).setCellValue("Country");
            headerRow.createCell(6).setCellValue("Duration");

            int rowNum = 1;
            for (Movie movie : movies) {
                Row row = sheet.createRow(rowNum++);
                //Category (List → String)
                String categories = movie.getCategories() == null ? "" :
                        movie.getCategories().stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.joining(","));

                //Director (List → String)
                String directors = movie.getDirectors() == null ? "" :
                        movie.getDirectors().stream()
                        .map(Director::getDirectorName)
                        .collect(Collectors.joining(","));

                //Country
                String country = movie.getCountry() == null ? "" :
                        movie.getCountry().getCountryName();

                row.createCell(0).setCellValue(movie.getTitle());
                row.createCell(1).setCellValue(categories);
                row.createCell(2).setCellValue(movie.getDescription());
                row.createCell(3).setCellValue(movie.getReleaseDate());
                row.createCell(4).setCellValue(directors);
                row.createCell(5).setCellValue(country);
                row.createCell(6).setCellValue(movie.getDuration());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e) {
            log.error("Error while exporting movies", e);
            throw new IOException(e);
        }
    }

    @Override
    public void importMovieFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // bỏ header

            List<Movie> batch = new ArrayList<>();
            int count = 0;

            while (rows.hasNext()) {
                Row row = rows.next();
                if(isRowEmpty(row)) continue;
                Movie movie = new Movie();
                // BASIC FIELD
                String title = UtilExcel.getCellString(row, 0);
                String categoryStr = UtilExcel.getCellString(row, 1);
                String description = UtilExcel.getCellString(row, 2);
                String releaseDateStr = UtilExcel.getCellString(row, 3);
                String directorStr = UtilExcel.getCellString(row, 4);
                String countryStr = UtilExcel.getCellString(row, 5);
                String durationStr = UtilExcel.getCellString(row, 6);
                movie.setTitle(title);
                movie.setDescription(description);

                if (!releaseDateStr.isEmpty()) {
                    movie.setReleaseDate(Integer.parseInt(releaseDateStr));
                }
                if (!durationStr.isEmpty()) {
                    movie.setDuration((long) Integer.parseInt(durationStr));
                }
                // CATEGORY
                List<Category> categories = Arrays.stream(categoryStr.split(","))
                        .filter(s -> !s.isBlank())
                        .map(String::trim)
                        .map(name -> categoryRepository.findByCategoryName(name)
                                .orElseGet(() -> {
                                    Category c = new Category();
                                    c.setCategoryName(name);
                                    return categoryRepository.save(c);
                                }))
                        .toList();
                movie.setCategories(categories);

                // DIRECTOR
                List<Director> directors = Arrays.stream(directorStr.split(","))
                        .filter(s -> !s.isBlank())
                        .map(String::trim)
                        .map(name -> directorRepository.findByDirectorName(name)
                                .orElseGet(() -> {
                                    Director d = new Director();
                                    d.setDirectorName(name);
                                    return directorRepository.save(d);
                                }))
                        .toList();
                movie.setDirectors(directors);

                // COUNTRY
                if (!countryStr.isBlank()) {
                    Country country = countryRepository.findByCountryName(countryStr)
                            .orElseGet(() -> {
                                Country c = new Country();
                                c.setCountryName(countryStr);
                                return countryRepository.save(c);
                            });
                    movie.setCountry(country);
                }
                batch.add(movie);
                count++;
                if (count % BATCH_SIZE == 0) {
                    movieRepository.saveAll(batch);
                    movieRepository.flush();
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                movieRepository.saveAll(batch);
                movieRepository.flush();
            }
        }
    }
}
