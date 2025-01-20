package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.LoginRequest;
import dev.lottery.tms.dto.request.RegisterRequest;
import dev.lottery.tms.dto.response.ErrorResponse;
import dev.lottery.tms.dto.response.MessageResponse;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.service.AuthService;
import dev.lottery.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication and token management")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns access and refresh tokens.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Wrong password", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User doesn't exist ", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MessageResponse login(
            @ParameterObject @ModelAttribute LoginRequest request,
            HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Registers a new user and returns user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserResponse register(
            @ParameterObject @ModelAttribute RegisterRequest request,
            HttpServletResponse response) {
        return userService.saveUser(request);
    }

    @PostMapping("/access-token/refresh")
    @Operation(summary = "Refresh access token", description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MessageResponse getNewAccessToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        return authService.updateAccessToken(request, response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh tokens", description = "Generates new access and refresh tokens using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MessageResponse refreshTokens(
            HttpServletRequest request,
            HttpServletResponse response) {
        return authService.updateTokens(request, response);
    }
}