package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.LoginRequest;
import dev.lottery.tms.dto.request.RegisterRequest;
import dev.lottery.tms.dto.response.MessageResponse;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.service.AuthService;
import dev.lottery.tms.service.UserService;
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
    private final UserService userService;

    @PostMapping("/login")
    public MessageResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        return userService.saveUser(request);
    }

    @PostMapping("/access-token/refresh")
    public MessageResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.updateAccessToken(request, response);
    }

    @PostMapping("/refresh")
    public MessageResponse refreshTokens(HttpServletRequest request, HttpServletResponse response) {
        return authService.updateTokens(request, response);
    }
}
