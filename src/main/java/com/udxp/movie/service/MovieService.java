package com.udxp.movie.service;

import com.udxp.movie.dto.request.MovieCreateRequest;
import com.udxp.movie.dto.response.MovieResponse;
import com.udxp.movie.entities.MovieDocument;
import com.udxp.movie.specification.MovieFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface MovieService {
    MovieResponse create(MovieCreateRequest request);

    MovieResponse getById(Long id);

    Page<MovieResponse> search(MovieFilter filter, Pageable pageable);

    MovieResponse update(Long id, MovieCreateRequest request);

    void delete(Long id);

    List<MovieDocument> searchAdvanced(String keyword, Integer releaseDate, String category);

    void exportToExcel(OutputStream outputStream) throws IOException;

    void importFromExcel(MultipartFile file) throws IOException;

}
