package dev.lottery.tms.dto.response;

import dev.lottery.tms.model.TaskPriority;
import dev.lottery.tms.model.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private UserResponse author;
    private UserResponse assignee;
    private List<CommentResponse> comments;
}
