package com.udxp.specification;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieFilter {
    String title;
    Integer releaseDate;
    String directorName;
    String categoryName;
    String countryName;
}
