package dev.lottery.tms.dto.request;

import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long assigneeId;
}
