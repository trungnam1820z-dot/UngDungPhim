package com.udxp.controller;

import com.udxp.dto.response.MovieResponse;
import com.udxp.mapper.MovieMapper;
import com.udxp.service.RecommendMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecommendMovieController {
    private final RecommendMovieService recommendMovieService;
    private final MovieMapper movieMapper;
    @GetMapping("/recommend")
    public Page<MovieResponse> recommend(@RequestParam Long userId,
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return recommendMovieService.recommend(userId,pageable)
                .map(movieMapper::toResponse);
    }
}
