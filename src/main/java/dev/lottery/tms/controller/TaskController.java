package dev.lottery.tms.controller;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.response.*;
import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import dev.lottery.tms.service.CommentService;
import dev.lottery.tms.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks and their related operations")
public class TaskController {
    private final TaskService taskService;
    private final CommentService commentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse createTask(@ParameterObject @ModelAttribute CreateTaskRequest request) {
        return taskService.createNewTask(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID", description = "Retrieves a task by its unique identifier. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse getTaskById(
            @Parameter(name = "id", description = "ID of the task to retrieve", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId) {
        return taskService.getTaskDtoById(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates an existing task with the provided details. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse updateTask(
            @Parameter(name = "id", description = "ID of the task to update", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @ParameterObject @ModelAttribute CreateTaskRequest request) {
        return taskService.updateTask(taskId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MessageResponse deleteTask(
            @Parameter(name = "id", description = "ID of the task to delete", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskService.isAssignee(#taskId)")
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update task status", description = "Updates the status of a task. Requires ADMIN role or being the assignee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse updateTaskStatus(
            @Parameter(name = "id", description = "ID of the task to update", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @Parameter(name = "status", description = "New status of the task", example = "IN_PROGRESS", required = true, in = ParameterIn.QUERY)
            @RequestParam(name = "status") TaskStatus taskStatus) {
        return taskService.updateTaskStatus(taskId, taskStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/priority")
    @Operation(summary = "Update task priority", description = "Updates the priority of a task. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task priority updated successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid priority provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse updateTaskPriority(
            @Parameter(name = "id", description = "ID of the task to update", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @Parameter(name = "priority", description = "New priority of the task", required = true, in = ParameterIn.QUERY)
            @RequestParam(name = "priority") TaskPriority taskPriority) {
        return taskService.updateTaskPriority(taskId, taskPriority);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskService.isAssignee(#taskId)")
    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a comment to a task", description = "Adds a comment to a task. Requires ADMIN role or being the assignee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully", content = @Content(schema = @Schema(implementation = CommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommentResponse leaveComment(
            @Parameter(name = "id", description = "ID of the task to add a comment to", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @Parameter(name = "text", description = "Text of the comment", example = "This is a comment", required = true, in = ParameterIn.QUERY)
            @RequestParam String text) {
        return taskService.addComment(taskId, text);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get comments for a task", description = "Retrieves all comments for a specific task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully", content = @Content(schema = @Schema(implementation = CommentsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommentsResponse getComments(
            @Parameter(name = "id", description = "ID of the task to retrieve comments for", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return commentService.getComments(taskId, page, size);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/assignee")
    @Operation(summary = "Update task assignee", description = "Updates the assignee of a task. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignee updated successfully", content = @Content(schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid assignee ID provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task or assignee not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TaskResponse updateAssignee(
            @Parameter(name = "id", description = "ID of the task to update", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long taskId,
            @Parameter(name = "assignee_id", description = "ID of the new assignee", example = "456", required = true, in = ParameterIn.QUERY)
            @RequestParam(name = "assignee_id") Long assigneeId) {
        return taskService.updateAssignee(taskId, assigneeId);
    }
}
