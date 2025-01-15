package dev.lottery.tms.service;

import dev.lottery.tms.dto.CreateUserRequest;
import dev.lottery.tms.dto.UserResponse;
import dev.lottery.tms.mapper.UserMapper;
import dev.lottery.tms.model.User;
import dev.lottery.tms.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse saveUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User requestedUser = userMapper.toUser(createUserRequest);
        User savedUser = userRepository.save(requestedUser);
        return userMapper.toUserResponse(savedUser);
    }

    public UserResponse findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
