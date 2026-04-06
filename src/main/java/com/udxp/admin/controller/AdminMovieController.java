package com.udxp.admin.controller;

import com.udxp.kafka.service.KafkaProducerService;
import com.udxp.movie.dto.request.MovieCreateRequest;
import com.udxp.movie.dto.response.MovieResponse;
import com.udxp.movie.entities.MovieDocument;
import com.udxp.movie.service.MovieService;
import com.udxp.movie.specification.MovieFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminMovieController {
    private final MovieService movieService;
    private final KafkaProducerService kafkaProducerService;
    private final RedisTemplate<String, Object> redisTemplate;

    // search, find, create, post, update, update 1 phần, delete

    @GetMapping("/search")
    public Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable) {
        return movieService.search(filter, pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody MovieCreateRequest request) {
        return movieService.create(request);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(@PathVariable Long id, @RequestBody MovieCreateRequest request) {
        return movieService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
    }

    @GetMapping("/search_advanced")
    public List<MovieDocument> searchMovieAdvanced(@RequestParam String keyword, @RequestParam(required = false) Integer releaseDate, @RequestParam(required = false) String category) {
        return movieService.searchAdvanced(keyword, releaseDate, category);
    }

    @GetMapping(value = "/export", produces =  MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportMovie( HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=movie.xlsx");
        movieService.exportToExcel(response.getOutputStream());
    }

    @PostMapping("/import")
    public ResponseEntity<String> importMovies(@RequestParam("file") MultipartFile file) throws IOException {
        movieService.importFromExcel(file);
        return ResponseEntity.ok("Import successful");
    }
}
