package dev.lottery.tms.util;

import dev.lottery.tms.exception.NotAuthenticatedException;
import dev.lottery.tms.security.impl.JwtAuthentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthUtils {
    public static void addTokenToCookie(
            @NonNull HttpServletResponse response, @NonNull String name, int maxAgeSecond, @NonNull String token) {
        Cookie cookie = new Cookie(name, token);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSecond);

        response.addCookie(cookie);
    }

    public static Optional<Cookie> getCookie(@NonNull HttpServletRequest request, @NonNull String name) {
        Cookie foundCookie = null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                foundCookie = cookie;
                break;
            }
        }

        return Optional.ofNullable(foundCookie);
    }

    public static JwtAuthentication getAuthentication() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new NotAuthenticatedException();
        }

        return auth;
    }
}
