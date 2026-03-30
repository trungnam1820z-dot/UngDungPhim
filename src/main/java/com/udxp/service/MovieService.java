package com.udxp.service;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.Movie;
import com.udxp.mapper.MovieMapper;
import com.udxp.repository.CategoryRepository;
import com.udxp.repository.CountryRepository;
import com.udxp.repository.DirectorRepository;
import com.udxp.repository.MovieRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieResponse createMovie(MovieCreateRequest request) {
        if (movieRepository.existsMovieByTitle(request.getTitle()))
            throw new EntityExistsException("Movie with title " + request.getTitle() + " already exists");
        Movie movie = movieMapper.toMovie(request);
        return movieMapper.toResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    public MovieResponse getMovieById(int id) {
        return movieMapper.toResponse(movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found")));
    }
    public MovieResponse getMovieByTitle(String title) {
        return movieMapper.toResponse(movieRepository.findMovieByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Movie with title: " + title + " not found")));
    }

    public List<MovieResponse> getMovieByDirector(String directorName) {
        return movieRepository.findByDirectors_directorNameContainingIgnoreCase(directorName)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    public List<MovieResponse> getMovieByCategory(String categoryName) {
        return movieRepository.findByCategories_categoryNameContainingIgnoreCase(categoryName)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }
    public List<MovieResponse> getMovieByCountry(String countryName) {
        return movieRepository.findMoviesByCountry_countryNameContainingIgnoreCase(countryName)
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }
    public MovieResponse updateMovie(int id, MovieCreateRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieMapper.updateMovie(movie, request);
        return movieMapper.toResponse(movieRepository.save(movie));
    }
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
        log.info("Movie with id: {} was deleted", id);
    }

}
