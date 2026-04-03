package com.udxp.service;

import com.udxp.entities.Category;
import com.udxp.entities.Director;
import com.udxp.entities.Movie;
import com.udxp.entities.UserBehavior;
import com.udxp.repository.MovieRepository;
import com.udxp.repository.UserBehaviorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendMovieService {
    private final UserBehaviorRepository behaviorRepository;
    private final MovieRepository movieRepository;

    public Page<Movie> recommend(Long userId, Pageable pageable) {
        List<Long> watchedMovieIds = behaviorRepository
                .findByUserIdAndActionIn(userId, List.of("WATCH", "LIKE"))
                .stream()
                .map(UserBehavior::getMovieId)
                .distinct()
                .toList();

        if (watchedMovieIds.isEmpty()) {
            return movieRepository.findAllByOrderByReleaseDateDesc(pageable);
        }
        List<Movie> watchedMovies = movieRepository.findAllById(watchedMovieIds);
        // Category
        Set<Long> categoryIds = watchedMovies.stream()
                .flatMap(m -> m.getCategories().stream())
                .map(Category::getId)
                .collect(Collectors.toSet());

        // Director
        Set<Long> directorIds = watchedMovies.stream()
                .flatMap(m -> m.getDirectors().stream())
                .map(Director::getId)
                .collect(Collectors.toSet());

        // Country
        Set<Long> countryIds = watchedMovies.stream()
                .map(m -> m.getCountry().getId())
                .collect(Collectors.toSet());

        Page<Movie> recommendedPage = movieRepository.recommendMovies(
                categoryIds,
                directorIds,
                countryIds,
                pageable
        );

        List<Movie> filtered = recommendedPage.getContent().stream()
                .filter(m -> !watchedMovieIds.contains(m.getId()))
                .toList();

        return new PageImpl<>(filtered, pageable, recommendedPage.getTotalElements()
        );
    }
}
