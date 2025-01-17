package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.CreateJwtRequest;
import dev.lottery.tms.dto.response.JwtResponse;
import dev.lottery.tms.dto.request.RefreshJwtRequest;
import dev.lottery.tms.service.AuthService;
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
    public JwtResponse login(@RequestBody CreateJwtRequest request) {
        return authService.login(request);
    }

    @PostMapping("/token")
    public JwtResponse getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        return authService.getNewAccessToken(request);
    }

    @PostMapping("/refresh")
    public JwtResponse getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        return authService.getNewRefreshToken(request);
    }
}
