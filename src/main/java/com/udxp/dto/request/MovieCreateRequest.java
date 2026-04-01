package com.udxp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieCreateRequest {
    @NotBlank(message = "Tên phim không được để trống")
    String title;
    String description;
    Integer releaseDate;
    int duration;
    int countryId;
    List<Integer> directorIds;
    List<Integer> categoryIds;
}
