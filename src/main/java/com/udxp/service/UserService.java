package com.udxp.service;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse updateUserById(Long id, UserCreateRequest request);
    UserResponse getUserById(Long id);
}
