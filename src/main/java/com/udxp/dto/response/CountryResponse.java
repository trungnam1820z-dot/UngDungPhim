package com.udxp.dto.response;

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
