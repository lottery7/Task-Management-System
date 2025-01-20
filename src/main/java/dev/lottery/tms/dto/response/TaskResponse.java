package dev.lottery.tms.dto.response;

import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object representing a task")
public class TaskResponse {
    @Schema(description = "Unique identifier of the task", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Title of the task", example = "Implement authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Current status of the task", example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED)
    private TaskStatus status;

    @Schema(description = "Priority level of the task", example = "HIGH", requiredMode = Schema.RequiredMode.REQUIRED)
    private TaskPriority priority;

    @Schema(description = "User who created the task", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserResponse author;

    @Schema(description = "User assigned to the task", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserResponse assignee;
}
