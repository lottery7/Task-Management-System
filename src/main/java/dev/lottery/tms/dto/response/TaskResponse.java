package dev.lottery.tms.dto.response;

import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private Long authorId;
    private Long assigneeId;
}
