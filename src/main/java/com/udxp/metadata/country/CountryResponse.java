package com.udxp.metadata.country;

import com.udxp.movie.dto.response.MovieResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryResponse {
    Long id;
    private String countryCode;
    String countryName;
    List<MovieResponse> movies;
}
