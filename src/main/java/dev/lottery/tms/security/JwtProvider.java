package dev.lottery.tms.security;

import dev.lottery.tms.entity.User;
import io.jsonwebtoken.Claims;
import lombok.NonNull;

public interface JwtProvider {
    String generateAccessToken(@NonNull User user);

    String generateRefreshToken(@NonNull User user);

    boolean validateAccessToken(@NonNull String accessToken);

    boolean validateRefreshToken(@NonNull String refreshToken);

    Claims getAccessClaims(@NonNull String token);

    Claims getRefreshClaims(@NonNull String token);
}
