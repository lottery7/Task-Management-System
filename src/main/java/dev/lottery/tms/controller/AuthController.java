package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.CreateJwtRequest;
import dev.lottery.tms.dto.response.JwtResponse;
import dev.lottery.tms.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody CreateJwtRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/token")
    public JwtResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.updateAccessToken(request, response);
    }

    @PostMapping("/refresh")
    public JwtResponse getNewRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.updateRefreshToken(request, response);
    }
}
