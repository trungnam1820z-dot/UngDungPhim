package com.udxp.metadata.director;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DirectorCreateRequest {
    String directorName;
    Long countryId;
}
