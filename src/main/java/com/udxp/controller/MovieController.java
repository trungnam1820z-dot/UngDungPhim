package com.udxp.controller;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.Movie;
import com.udxp.entities.MovieDocument;
import com.udxp.repository.MovieRepository;
import com.udxp.service.MovieService;
import com.udxp.specification.MovieFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;
    private final MovieRepository movieRepository;

    // search, find, create, post, update, update 1 phần, delete

    @GetMapping("/search")
    public Page<MovieResponse> searchMovie(MovieFilter filter,
                                           Pageable pageable) {
        return movieService.searchMovie(filter,pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody MovieCreateRequest request) {
        return movieService.createMovie(request);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(@PathVariable Long id,
                                     @RequestBody MovieCreateRequest request) {
        return movieService.updateMovie(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
    @GetMapping("/search_advanced")
    public List<MovieDocument> searchMovieAdvanced(@RequestParam String keyword,
                                                   @RequestParam(required = false) Integer releaseDate,
                                                   @RequestParam(required = false) String category){
        return movieService.searchAdvanced(keyword,releaseDate,category);
    }
    @GetMapping("/export")
    public ResponseEntity<InputStreamSource> exportMovie() throws IOException {
        List<Movie> movies = movieRepository.findAll();
        ByteArrayInputStream in = movieService.exportMovieToExcel(movies);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=movie.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
    @PostMapping("/import")
    public ResponseEntity<String> importMovies(@RequestParam("file") MultipartFile file) throws IOException {
        movieService.importMovieFromExcel(file);
        return ResponseEntity.ok("Import successful");
    }
}
