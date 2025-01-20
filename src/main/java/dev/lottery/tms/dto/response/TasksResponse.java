package dev.lottery.tms.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TasksResponse {
    List<TaskResponse> tasks;
}
