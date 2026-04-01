package com.udxp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CountryCreateRequest {
    String countryCode;
    @NotBlank(message = "Tên quốc gia không được để trống")
    String countryName;
}
