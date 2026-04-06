package com.udxp.authentication.controller;

import com.udxp.authentication.dto.LoginRequest;
import com.udxp.authentication.service.LoginService;
import com.udxp.movie.dto.response.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/login")
    public Page<MovieResponse> login(@RequestBody LoginRequest request, Pageable pageable) {
        return loginService.login(request,pageable);
    }
}
