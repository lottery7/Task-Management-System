package dev.lottery.tms.security.impl;

import dev.lottery.tms.entity.User;
import dev.lottery.tms.security.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtProviderImpl implements JwtProvider {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;
    private final long accessExpirationMs;
    private final long refreshExpirationMs;

    public JwtProviderImpl(
            @Value("${jwt.access.secret}") String accessSecret,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            @Value("${jwt.access.expiration-minutes}") String accessExpirationMinutes,
            @Value("${jwt.refresh.expiration-days}") String refreshExpirationDays
    ) {
        this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
        this.accessExpirationMs = TimeUnit.MINUTES.toMillis(Long.parseLong(accessExpirationMinutes));
        this.refreshExpirationMs = TimeUnit.DAYS.toMillis(Long.parseLong(refreshExpirationDays));
    }

    private static String generateToken(
            @NonNull User user, @NonNull SecretKey secret, Long duration, Map<String, ?> claims) {

        final long currentTimeMs = System.currentTimeMillis();

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(currentTimeMs))
                .expiration(new Date(currentTimeMs + duration))
                .signWith(secret)
                .claims(claims)
                .compact();
    }

    private static boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Claims ignored = getClaims(token, secret);
            return true;

        } catch (ExpiredJwtException ignored) {
            log.error("Token expired");

        } catch (UnsupportedJwtException ignored) {
            log.error("Unsupported jwt");

        } catch (MalformedJwtException ignored) {
            log.error("Malformed jwt");

        } catch (SignatureException ignored) {
            log.error("Invalid signature");

        } catch (Exception e) {
            log.error("invalid token", e);
        }

        return false;
    }

    private static Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static Map<String, ?> getAccessClaims(@NonNull User user) {
        return Map.of(
                "roles", user.getRoles(),
                "name", user.getName());
    }

    private static Map<String, ?> getRefreshClaims(@NonNull User user) {
        return Map.of();
    }

    @Override
    public String generateAccessToken(@NonNull User user) {
        return generateToken(user, accessSecret, accessExpirationMs, getAccessClaims(user));
    }

    @Override
    public String generateRefreshToken(@NonNull User user) {
        return generateToken(user, refreshSecret, refreshExpirationMs, getRefreshClaims(user));
    }

    @Override
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, accessSecret);
    }

    @Override
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, refreshSecret);
    }

    @Override
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, accessSecret);
    }

    @Override
    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, refreshSecret);
    }
}
