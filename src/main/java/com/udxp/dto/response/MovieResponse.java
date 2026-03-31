package com.udxp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieResponse {
    String title;
    String description;
    Integer releaseDate;
    int duration;
    List<String> directorName;
    List<String> categoryName;
    String countryName;
}
