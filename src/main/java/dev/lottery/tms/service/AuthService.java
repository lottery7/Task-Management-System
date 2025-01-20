package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.CreateJwtRequest;
import dev.lottery.tms.dto.response.JwtResponse;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.InvalidJwtTokenException;
import dev.lottery.tms.exception.WrongPasswordException;
import dev.lottery.tms.security.JwtProvider;
import dev.lottery.tms.security.impl.JwtAuthentication;
import dev.lottery.tms.util.AuthUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String accessTokenCookieName = "JWT_access";
    private static final String refreshTokenCookieName = "JWT_refresh";

    private final UserService userService;
    private final JwtProvider jwtProvider;

    private void addAccessTokenCookie(@NonNull HttpServletResponse response, @NonNull String token) {
        AuthUtils.addTokenToCookie(response, accessTokenCookieName, jwtProvider.getAccessExpirationSeconds(), token);
    }

    private void addRefreshTokenCookie(@NonNull HttpServletResponse response, @NonNull String token) {
        AuthUtils.addTokenToCookie(response, refreshTokenCookieName, jwtProvider.getRefreshExpirationSeconds(), token);
    }

    public Optional<String> getAccessTokenFromCookie(@NonNull HttpServletRequest request) {
        return AuthUtils.getCookie(request, accessTokenCookieName).map(Cookie::getValue);
    }

    public Optional<String> getRefreshTokenFromCookie(@NonNull HttpServletRequest request) {
        return AuthUtils.getCookie(request, refreshTokenCookieName).map(Cookie::getValue);
    }

    public JwtResponse login(@NonNull CreateJwtRequest request, @NonNull HttpServletResponse response) {
        User user = userService.getUserEntityByEmail(request.getEmail());
        if (!user.getPassword().equals(request.getPassword())) {
            throw new WrongPasswordException();
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);

        return JwtResponse.builder().message("Success").build();
    }

    public JwtResponse updateAccessToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request).orElseThrow(InvalidJwtTokenException::new);

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);

            addAccessTokenCookie(response, newAccessToken);

            return JwtResponse.builder().message("Success").build();
        }

        throw new InvalidJwtTokenException();
    }

    public JwtResponse updateRefreshToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request).orElseThrow(InvalidJwtTokenException::new);

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);
            String newRefreshToken = jwtProvider.generateRefreshToken(user);

            addAccessTokenCookie(response, newAccessToken);
            addRefreshTokenCookie(response, newRefreshToken);

            return JwtResponse.builder().message("Success").build();
        }

        throw new InvalidJwtTokenException();
    }

    public JwtAuthentication getAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
