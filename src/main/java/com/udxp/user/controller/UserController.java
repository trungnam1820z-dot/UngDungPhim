package com.udxp.user.controller;

import com.udxp.kafka.service.KafkaProducerService;
import com.udxp.movie.dto.response.MovieResponse;
import com.udxp.movie.entities.Movie;
import com.udxp.movie.entities.MovieDocument;
import com.udxp.movie.service.MovieService;
import com.udxp.movie.specification.MovieFilter;
import com.udxp.user.dto.request.UserCreateRequest;
import com.udxp.user.dto.response.UserResponse;
import com.udxp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final KafkaProducerService kafkaProducerService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MovieService movieService;

    @PostMapping("/register")
    public UserResponse create(@RequestBody UserCreateRequest request){
        return userService.create(request);
    }
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getById(id);
    }
    @PutMapping("{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserCreateRequest request){
        return userService.updateById(id,request);
    }

    @GetMapping("/search")
    public Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable) {
        return movieService.search(filter, pageable);
    }
    @GetMapping("/search_advanced")
    public List<MovieDocument> searchMovieAdvanced(@RequestParam String keyword, @RequestParam(required = false) Integer releaseDate, @RequestParam(required = false) String category) {
        return movieService.searchAdvanced(keyword, releaseDate, category);
    }

    @PostMapping("/watch")
    public ResponseEntity<?> watchMovie(Long userId, Long movieId) {
        kafkaProducerService.sendUserBehaviorEvent(userId, movieId, "WATCH");
        return ResponseEntity.ok("Watch event sent");
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeMovie(Long userId, Long movieId) {
        kafkaProducerService.sendUserBehaviorEvent(userId, movieId, "LIKE");
        return ResponseEntity.ok("Like event sent");
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(Long userId) {
        kafkaProducerService.sendUserBehaviorEvent(userId, null, "SEARCH");
        return ResponseEntity.ok("Search tracked");
    }
    @GetMapping("/recommend")
    public List<Movie> getRecommend(Long userId) {
        return (List<Movie>) redisTemplate.opsForValue().get("recommend:" + userId);
    }
}
