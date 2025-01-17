package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.CreateUserRequest;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserDtoByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.saveUser(createUserRequest);
    }
}
