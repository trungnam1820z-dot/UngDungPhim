package com.udxp.authentication.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.udxp.authentication.dto.request.LoginRequest;
import com.udxp.authentication.dto.request.RefreshTokenRequest;
import com.udxp.authentication.dto.response.LoginResponse;
import com.udxp.authentication.dto.response.RefreshTokenResponse;
import com.udxp.authentication.entities.RefreshToken;
import com.udxp.authentication.repository.RefreshTokenRepository;
import com.udxp.user.entities.User;
import com.udxp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    @NonFinal
    @Value("${jwt.secret_key}")
    protected String secretKey;
    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            log.error("Invalid password");
            throw new BadCredentialsException("Incorrect password");
        }
        var accessToken = generateAccessToken(user);
        return LoginResponse.builder()
                .token(accessToken)
                .build();
    }
    public String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("UngDungXemPhim")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("scope", List.of(user.getRole().name()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Error signing JWS object");
            throw new RuntimeException(e);
        }
    }
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setId(user.getId());
        refreshToken.setExpiresDate(Instant.now().plus(1, ChronoUnit.DAYS));
        refreshToken.setRevoked(false);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (token.isRevoked() || token.getExpiresDate().isBefore(Instant.now())) {
            throw new BadCredentialsException("token expired or revoked");
        }
        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        token.setRevoked(true);
        refreshTokenRepository.save(token);

        RefreshToken newRefreshToken = createRefreshToken(user);
        String newAccessToken = generateAccessToken(user);

        return  RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
    public void logout(@RequestBody RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
