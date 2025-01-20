package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.mapper.TaskMapper;
import dev.lottery.tms.respository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponse createNewTask(CreateTaskRequest createTaskRequest) {
        Task task = taskMapper.toTask(createTaskRequest);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }
}
