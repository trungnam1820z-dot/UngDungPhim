package com.udxp.service;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;
import com.udxp.specification.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    UserResponse updateUserById(int id, UserCreateRequest request);
    Page<UserResponse> getUsers(UserFilter filter, Pageable pageable);
    UserResponse getUserById(int id);
    void deleteUserById(int id);
}
