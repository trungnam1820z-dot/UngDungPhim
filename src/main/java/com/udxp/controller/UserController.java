package com.udxp.controller;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;
import com.udxp.service.UserService;
import com.udxp.specification.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create_user")
    public UserResponse createUser(@RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/users")
    public Page<UserResponse> getUsers(@ModelAttribute UserFilter filter, @PageableDefault(size = 5) Pageable pageable){
        return userService.getUsers(filter,pageable);
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }
    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable int id, @RequestBody UserCreateRequest request){
        return userService.updateUserById(id,request);
    }
    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable int id){
        userService.deleteUserById(id);
    }
}
