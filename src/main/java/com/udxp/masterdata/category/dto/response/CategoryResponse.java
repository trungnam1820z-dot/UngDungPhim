package com.udxp.masterdata.category.dto.response;

import com.udxp.movie.dto.response.MovieResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String categoryName;
    String description;
    List<MovieResponse> movies;
}
