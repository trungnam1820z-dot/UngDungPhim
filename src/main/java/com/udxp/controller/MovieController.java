package com.udxp.controller;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.MovieDocument;
import com.udxp.service.MovieService;
import com.udxp.specification.MovieFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    // search, find, create, post, update, update 1 phần, delete

    @GetMapping("/search")
    public Page<MovieResponse> searchMovie(MovieFilter filter,
                                           Pageable pageable) {
        return movieService.searchMovie(filter,pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody MovieCreateRequest request) {
        return movieService.createMovie(request);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(@PathVariable int id,
                                     @RequestBody MovieCreateRequest request) {
        return movieService.updateMovie(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
    }
    @GetMapping("/search_advanced")
    public List<MovieDocument> searchMovieAdvanced(@RequestParam String keyword,
                                                   @RequestParam(required = false) Integer releaseDate,
                                                   @RequestParam(required = false) String category){
        return movieService.searchAdvanced(keyword,releaseDate,category);
    }
}
