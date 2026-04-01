package com.udxp.service;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;
import com.udxp.entities.User;
import com.udxp.mapper.UserMapper;
import com.udxp.repository.UserRepository;
import com.udxp.specification.UserFilter;
import com.udxp.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public UserResponse updateUserById(int id, UserCreateRequest request) {
        User user =  userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> getUsers(UserFilter filter, Pageable pageable) {
        Specification<User> spec = Specification
                .where(UserSpecification.createdAfter(filter.getCreateAt()))
                .and(UserSpecification.createdBefore(filter.getCreateAt()));
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("createAt").ascending()
        );
        Page<User> userPage = userRepository.findAll(spec, sortedPageable);
        return userPage.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse getUserById(int id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
