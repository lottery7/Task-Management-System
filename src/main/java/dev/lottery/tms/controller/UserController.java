package dev.lottery.tms.controller;

import dev.lottery.tms.dto.response.ErrorResponse;
import dev.lottery.tms.dto.response.TasksResponse;
import dev.lottery.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "APIs for managing users and their tasks")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}/tasks/assigned")
    @Operation(summary = "Get assigned tasks for a user", description = "Retrieves a paginated list of tasks assigned to a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", content = @Content(schema = @Schema(implementation = TasksResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TasksResponse getUserAssignedTasks(
            @Parameter(name = "id", description = "ID of the user", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long userId,
            @Parameter(name = "page", description = "Page number (zero-based)", example = "0", required = true, in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(name = "size", description = "Number of items per page", example = "10", required = true, in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "10") int size) {
        return userService.getAssignedTasksById(userId, page, size);
    }

    @GetMapping("/{id}/tasks/authored")
    @Operation(summary = "Get authored tasks for a user", description = "Retrieves a paginated list of tasks authored by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", content = @Content(schema = @Schema(implementation = TasksResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public TasksResponse getUserAuthoredTasks(
            @Parameter(name = "id", description = "ID of the user", example = "123", required = true, in = ParameterIn.PATH)
            @PathVariable(name = "id") Long userId,
            @Parameter(name = "page", description = "Page number (zero-based)", example = "0", required = true, in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(name = "size", description = "Number of items per page", example = "10", required = true, in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "10") int size) {
        return userService.getAuthoredTasksById(userId, page, size);
    }
}