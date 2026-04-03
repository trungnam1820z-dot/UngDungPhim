package com.udxp.service;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;
import com.udxp.entities.User;
import com.udxp.mapper.UserMapper;
import com.udxp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        if(userRepository.existsUsersByUserName(request.getUserName())){
            log.error("Username already exists");
            throw new RuntimeException("Username already exists");
        }
        User user = userMapper.toUserEntity(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public UserResponse updateUserById(Long id, UserCreateRequest request) {
        User user =  userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

}
