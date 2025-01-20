package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.*;
import dev.lottery.tms.dto.response.CommentResponse;
import dev.lottery.tms.dto.response.CommentsResponse;
import dev.lottery.tms.dto.response.MessageResponse;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return taskService.getTaskDtoById(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable(name = "id") Long taskId, UpdateTaskRequest request) {
        return taskService.updateTask(taskId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public MessageResponse deleteTask(@PathVariable(name = "id") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskService.isAssignee(#taskId)")
    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(@PathVariable(name = "id") Long taskId, UpdateTaskStatusRequest request) {
        return taskService.updateTaskStatus(taskId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/priority")
    public TaskResponse updateTaskPriority(@PathVariable(name = "id") Long taskId, UpdateTaskPriorityRequest request) {
        return taskService.updateTaskPriority(taskId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskService.isAssignee(#taskId)")
    @PostMapping("/{id}/comments")
    public CommentResponse leaveComment(@PathVariable(name = "id") Long taskId, CreateCommentRequest request) {
        return taskService.addComment(taskId, request);
    }

    @GetMapping("/{id}/comments")
    public CommentsResponse getComments(@PathVariable(name = "id") Long taskId) {
        return taskService.getComments(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/assignee")
    public TaskResponse updateAssignee(@PathVariable(name = "id") Long taskId, UpdateAssigneeRequest request) {
        return taskService.updateAssignee(taskId, request);
    }
}
