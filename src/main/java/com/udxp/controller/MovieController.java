package com.udxp.controller;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;
    @PostMapping
    public MovieResponse createMovie(MovieCreateRequest request) {
        return movieService.createMovie(request);
    }
    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }
    @GetMapping("/{title}")
    public MovieResponse getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }
    @GetMapping
    public List<MovieResponse> getAllMovies() {
        return movieService.getAllMovies();
    }
    @GetMapping("/{categoryName}")
    public List<MovieResponse> getAllMoviesByCategory(@PathVariable String categoryName) {
        return movieService.getMovieByCategory(categoryName);
    }
    @GetMapping("/{countryName}")
    public List<MovieResponse> getAllMoviesByCountry(@PathVariable String countryName) {
        return movieService.getMovieByCountry(countryName);
    }
    @GetMapping("/{directorName}")
    public List<MovieResponse> getAllMoviesByDirector(@PathVariable String directorName) {
        return movieService.getMovieByDirector(directorName);
    }
    @PutMapping("/{id}")
    public MovieResponse updateMovie(@PathVariable int id ,@RequestBody MovieCreateRequest request) {
        return movieService.updateMovie(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
    }
}
