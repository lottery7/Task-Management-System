package dev.lottery.tms.dto.request;

import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @Schema(description = "Title of the task",
            example = "Implement user authentication",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Detailed description of the task",
            example = "Develop and integrate OAuth2.0 for user authentication",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "Current status of the task",
            requiredMode = Schema.RequiredMode.REQUIRED,
            defaultValue = "PENDING")
    private TaskStatus status;

    @Schema(description = "Priority level of the task",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private TaskPriority priority;

    @Schema(description = "ID of the user assigned to the task",
            example = "123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long assigneeId;
}


