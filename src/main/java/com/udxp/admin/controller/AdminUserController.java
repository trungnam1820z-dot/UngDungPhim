package com.udxp.admin.controller;

import com.udxp.admin.service.AdminService;
import com.udxp.authentication.Role;
import com.udxp.user.dto.response.UserResponse;
import com.udxp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUserController {
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/users")
    public Page<UserResponse> getUsers(Pageable pageable) {
        return adminService.getUsers(pageable);
    }

    @PutMapping("/{id}/role")
    public void updateRole(@PathVariable Long id, @RequestParam Role role) {
        adminService.updateRole(id, role);
    }
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getById(id);
    }
}
