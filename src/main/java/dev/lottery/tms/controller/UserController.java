package dev.lottery.tms.controller;

import dev.lottery.tms.dto.response.TasksResponse;
import dev.lottery.tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}/tasks/assigned")
    public TasksResponse getUserAssignedTasks(@PathVariable(name = "id") Long userId) {
        return userService.getAllAssignedTasksById(userId);
    }

    @GetMapping("/{id}/tasks/authored")
    public TasksResponse getUserAuthoredTasks(@PathVariable(name = "id") Long userId) {
        return userService.getAllAuthoredTasksById(userId);
    }
}
