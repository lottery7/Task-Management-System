package dev.lottery.tms.dto.request;

import dev.lottery.tms.model.TaskPriority;
import lombok.Data;

@Data
public class UpdateTaskPriorityRequest {
    private TaskPriority priority;
}
