package com.udxp.dto.response;

import com.udxp.entities.Movie;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectorResponse {
    String DirectorName;
    String countryName;
    List<Movie> movies;
}
