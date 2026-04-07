package com.udxp.masterdata.director.dto.response;

import com.udxp.movie.dto.response.MovieResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectorResponse {
    String directorName;
    String countryName;
    List<MovieResponse> movies;
}
