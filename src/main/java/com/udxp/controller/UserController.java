package com.udxp.controller;

import com.udxp.dto.request.UserCreateRequest;
import com.udxp.dto.response.UserResponse;
import com.udxp.service.UserService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserCreateRequest request){
        return userService.updateUserById(id,request);
    }
}
