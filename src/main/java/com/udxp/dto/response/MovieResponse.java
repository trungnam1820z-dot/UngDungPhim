package com.udxp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieResponse  implements Serializable {
    String title;
    String description;
    Integer releaseDate;
    int duration;
    List<String> directorName;
    List<String> categoryName;
    String countryName;
}
