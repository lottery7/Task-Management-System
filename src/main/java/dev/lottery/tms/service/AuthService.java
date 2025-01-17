package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.CreateJwtRequest;
import dev.lottery.tms.dto.request.RefreshJwtRequest;
import dev.lottery.tms.dto.response.JwtResponse;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.InvalidJwtTokenException;
import dev.lottery.tms.exception.WrongPasswordException;
import dev.lottery.tms.security.JwtProvider;
import dev.lottery.tms.security.impl.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull CreateJwtRequest request) {
        User user = userService.getUserEntityByEmail(request.getEmail());
        if (!user.getPassword().equals(request.getPassword())) {
            throw new WrongPasswordException();
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    public JwtResponse getNewAccessToken(RefreshJwtRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);

            return JwtResponse.builder().accessToken(newAccessToken).build();
        }

        throw new InvalidJwtTokenException();
    }

    public JwtResponse getNewRefreshToken(RefreshJwtRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String email = claims.getSubject();

            User user = userService.getUserEntityByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(user);
            String newRefreshToken = jwtProvider.generateRefreshToken(user);

            return JwtResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken).build();
        }

        throw new InvalidJwtTokenException();
    }

    public JwtAuthentication getAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
