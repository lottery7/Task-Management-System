package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.CreateUserRequest;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.exception.EmailInUseException;
import dev.lottery.tms.exception.UserNotFoundException;
import dev.lottery.tms.mapper.UserMapper;
import dev.lottery.tms.model.Role;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse saveUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new EmailInUseException();
        }

        User requestedUser = userMapper.toUser(createUserRequest);
        User savedUser = userRepository.save(requestedUser);
        return userMapper.toUserResponse(savedUser);
    }

    public User getUserEntityByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse getUserDtoByEmail(String email) {
        return userMapper.toUserResponse(getUserEntityByEmail(email));
    }

    public Set<Role> getUserRolesByEmail(String email) {
        User user = getUserEntityByEmail(email);
        return user.getRoles();
    }
}
