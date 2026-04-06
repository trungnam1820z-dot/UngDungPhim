package com.udxp.authentication.service;

import com.udxp.authentication.dto.LoginRequest;
import com.udxp.movie.dto.response.MovieResponse;
import com.udxp.movie.entities.Movie;
import com.udxp.mapper.MovieMapper;
import com.udxp.movie.repository.MovieRepository;
import com.udxp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public Page<MovieResponse> login(LoginRequest request, Pageable pageable) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new RuntimeException("Incorrect username or password");
        }
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("title").ascending()
        );
        Page<Movie> moviePage = movieRepository.findAll(sortedPageable);
        return moviePage.map(movieMapper::toResponse);
    }
}
