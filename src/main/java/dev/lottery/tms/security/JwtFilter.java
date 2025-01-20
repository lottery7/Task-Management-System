package dev.lottery.tms.security;

import dev.lottery.tms.security.impl.JwtAuthentication;
import dev.lottery.tms.service.AuthService;
import dev.lottery.tms.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        Optional<String> accessToken = authService.getAccessTokenFromCookie((HttpServletRequest) servletRequest);

        if (accessToken.isPresent() && jwtProvider.validateAccessToken(accessToken.get())) {

            Claims claims = jwtProvider.getAccessClaims(accessToken.get());

            JwtAuthentication jwtInfoToken = JwtUtils.generateAuth(claims);
            jwtInfoToken.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
