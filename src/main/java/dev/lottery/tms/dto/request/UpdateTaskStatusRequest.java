package dev.lottery.tms.dto.request;

import dev.lottery.tms.model.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
