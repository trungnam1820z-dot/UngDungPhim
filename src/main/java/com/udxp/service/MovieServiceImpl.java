package com.udxp.service;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.Movie;
import com.udxp.mapper.MovieMapper;
import com.udxp.repository.MovieRepository;
import com.udxp.specification.MovieFilter;
import com.udxp.specification.MovieSpecification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    @Override
    public MovieResponse createMovie(MovieCreateRequest request) {
        if (movieRepository.existsMovieByTitle(request.getTitle())){
            log.error("Movie with title: {}", request.getTitle() + "already exists");
            throw new EntityExistsException("Movie with title " + request.getTitle() + " already exists");
        }

        Movie movie = movieMapper.toMovie(request);
        log.info("Movie with title: {} has been created", request.getTitle());
        return movieMapper.toResponse(movieRepository.save(movie));
    }

    public MovieResponse getMovieById(int id) {
        return movieMapper.toResponse(movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found")));
    }
    @Override
    public Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable) {
        Specification<Movie> spec = Specification
                .where(MovieSpecification.hasTitle(filter.getTitle()))
                .and(MovieSpecification.hasReleaseDate(Integer.valueOf(filter.getReleaseDate())))
                .and(MovieSpecification.hasDirectorName(filter.getDirectorName()))
                .and(MovieSpecification.hasCategoryName(filter.getCategoryName()))
                .and(MovieSpecification.hasCountryName(filter.getCountryName()));
        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);
        return moviePage.map(movieMapper::toResponse);
    }
    @Override
    public MovieResponse updateMovie(int id, MovieCreateRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        log.error("Movie with id: {} not found", id);
        movieMapper.updateMovie(movie, request);
        log.info("Movie with id: {} has been updated", id);
        return movieMapper.toResponse(movieRepository.save(movie));
    }
    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
        log.info("Movie with id: {} was deleted", id);
    }
}
