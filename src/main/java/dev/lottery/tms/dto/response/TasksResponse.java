package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Response object containing a list of tasks")
public class TasksResponse {
    @Schema(description = "List of tasks", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TaskResponse> tasks;
}
