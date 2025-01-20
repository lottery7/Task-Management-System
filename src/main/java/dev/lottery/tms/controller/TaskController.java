package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskStatusRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public TaskResponse createTask(CreateTaskRequest request) {
        return taskService.createNewTask(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable(name = "id") Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable(name = "id") Long taskId, UpdateTaskRequest request) {
        return taskService.updateTask(taskId, request);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(@PathVariable(name = "id") Long taskId, UpdateTaskStatusRequest request)
            throws AccessDeniedException {
        return taskService.updateTaskStatus(taskId, request);
    }
}
