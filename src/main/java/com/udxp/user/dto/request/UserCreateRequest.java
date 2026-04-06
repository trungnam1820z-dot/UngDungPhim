package com.udxp.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "UserName không được để trống")
    String userName;
    @NotBlank(message = "Password không được để trống")
    String password;
    String fullName;
    String email;
    String gender;
}
