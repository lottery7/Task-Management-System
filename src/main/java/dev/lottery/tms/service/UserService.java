package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.RegisterRequest;
import dev.lottery.tms.dto.response.TasksResponse;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.EmailInUseException;
import dev.lottery.tms.exception.UserNotFoundException;
import dev.lottery.tms.mapper.TaskMapper;
import dev.lottery.tms.mapper.UserMapper;
import dev.lottery.tms.respository.TaskRepository;
import dev.lottery.tms.respository.UserRepository;
import dev.lottery.tms.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    public UserResponse saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailInUseException();
        }

        User requestedUser = userMapper.toUser(registerRequest);
        User savedUser = userRepository.save(requestedUser);
        return userMapper.toUserResponse(savedUser);
    }

    public User getCurrentUserEntity() {
        String email = getCurrentUserEmail();
        return getUserEntityByEmail(email);
    }

    public String getCurrentUserEmail() {
        return AuthUtils.getAuthentication().getEmail();
    }

    public User getUserEntityByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public TasksResponse getAssignedTasksById(Long id, int page, int size) {
        User ignored = getUserEntityById(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasksPage = taskRepository.findByAssigneeId(id, pageable);
        return taskMapper.toTasksResponse(tasksPage.getContent());
    }

    public TasksResponse getAuthoredTasksById(Long id, int page, int size) {
        User ignored = getUserEntityById(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasksPage = taskRepository.findByAuthorId(id, pageable);
        return taskMapper.toTasksResponse(tasksPage.getContent());
    }
}
