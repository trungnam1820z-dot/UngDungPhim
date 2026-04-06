package com.udxp.kafka.service;

import com.udxp.movie.entities.Movie;
import com.udxp.kafka.UserBehaviorEvent;
import com.udxp.kafka.entities.UserBehavior;
import com.udxp.kafka.repository.BehaviorRepository;
import com.udxp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaRecommendationConsumer {
    private final BehaviorRepository behaviorRepository;
    private final MovieRepository movieRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "user-behavior", groupId = "recommend-group")
    public void handle(UserBehaviorEvent event) {

        Long userId = event.getUserId();

        List<Long> watchedMovieIds = behaviorRepository
                .findByUserId(userId)
                .stream()
                .map(UserBehavior::getMovieId)
                .distinct()
                .toList();

        List<Movie> recommendMovies = movieRepository.findRecommend(watchedMovieIds);

        redisTemplate.opsForValue().set("recommend:" + userId, recommendMovies);
    }
}
