package com.udxp.service;

import com.udxp.dto.request.MovieCreateRequest;
import com.udxp.dto.response.MovieResponse;
import com.udxp.entities.Movie;
import com.udxp.entities.MovieDocument;
import com.udxp.specification.MovieFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieResponse createMovie(MovieCreateRequest request);

    MovieResponse getMovieById(Long id);

    Page<MovieResponse> searchMovie(MovieFilter filter, Pageable pageable);

    MovieResponse updateMovie(Long id, MovieCreateRequest request);

    void deleteMovie(Long id);

    List<MovieDocument> searchAdvanced(String keyword, Integer releaseDate, String category);
    ByteArrayInputStream exportMovieToExcel(List<Movie> movies) throws IOException;
    void importMovieFromExcel(MultipartFile file) throws IOException;
}
