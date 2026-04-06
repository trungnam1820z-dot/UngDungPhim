package com.udxp.user.service;

import com.udxp.user.dto.request.UserCreateRequest;
import com.udxp.user.dto.response.UserResponse;

public interface UserService {
    UserResponse create(UserCreateRequest request);
    UserResponse updateById(Long id, UserCreateRequest request);
    UserResponse getById(Long id);
}
