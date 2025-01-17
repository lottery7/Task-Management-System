package dev.lottery.tms.controller;

import dev.lottery.tms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security")
@RequiredArgsConstructor
public class SecurityTestController {
    private final AuthService authService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getHelloUser() {
        Authentication auth = authService.getAuthentication();
        return String.format("Hello, user %s!", auth.getName());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getHelloAdmin() {
        Authentication auth = authService.getAuthentication();
        return String.format("Hi, admin %s!", auth.getName());
    }
}
