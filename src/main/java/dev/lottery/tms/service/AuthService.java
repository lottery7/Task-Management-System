package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.LoginRequest;
import dev.lottery.tms.dto.response.MessageResponse;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.InvalidJwtTokenException;
import dev.lottery.tms.exception.WrongPasswordException;
import dev.lottery.tms.security.JwtProvider;
import dev.lottery.tms.util.AuthUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final String accessTokenCookieName = "JWT_access";
    private static final String refreshTokenCookieName = "JWT_refresh";

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

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

    public MessageResponse login(@NonNull LoginRequest request, @NonNull HttpServletResponse response) {
        User user = userService.getUserEntityByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);

        return new MessageResponse("Logged in successfully");
    }

    public MessageResponse updateAccessToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request).orElseThrow(InvalidJwtTokenException::new);

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);

            addAccessTokenCookie(response, newAccessToken);

            return new MessageResponse("Access token updated successfully");
        }

        throw new InvalidJwtTokenException();
    }

    public MessageResponse updateTokens(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromCookie(request).orElseThrow(InvalidJwtTokenException::new);

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);
            String newRefreshToken = jwtProvider.generateRefreshToken(user);

            addAccessTokenCookie(response, newAccessToken);
            addRefreshTokenCookie(response, newRefreshToken);

            return new MessageResponse("Tokens updated successfully");
        }

        throw new InvalidJwtTokenException();
    }
}
