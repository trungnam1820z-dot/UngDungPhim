package com.udxp.service;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.MovieDocument;
import com.udxp.specification.MovieFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    MovieResponse createMovie(MovieCreateRequest request);

    MovieResponse getMovieById(int id);

    Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable);

    MovieResponse updateMovie(int id, MovieCreateRequest request);

    void deleteMovie(int id);

    List<MovieDocument> searchAdvanced(String keyword, Integer releaseDate, String category);
}
