package com.udxp.mapper;

import com.udxp.user.dto.request.UserCreateRequest;
import com.udxp.user.dto.response.UserResponse;
import com.udxp.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserEntity(UserCreateRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserCreateRequest request);
}
