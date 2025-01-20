package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.TaskNotFoundException;
import dev.lottery.tms.exception.UserNotFoundException;
import dev.lottery.tms.mapper.TaskMapper;
import dev.lottery.tms.respository.TaskRepository;
import dev.lottery.tms.respository.UserRepository;
import dev.lottery.tms.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskResponse createNewTask(CreateTaskRequest createTaskRequest) {
        String authorEmail = AuthUtils.getAuthentication().getEmail();
        User author = userRepository.findByEmail(authorEmail).orElseThrow(UserNotFoundException::new);

        Task task = taskMapper.toTask(createTaskRequest);
        task.setAuthor(author);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toTaskResponse(savedTask);
    }

    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        return taskMapper.toTaskResponse(task);
    }

    public TaskResponse updateTask(Long taskId, UpdateTaskRequest updateTaskRequest) {
        Task foundTask = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        Task task = taskMapper.toTask(updateTaskRequest);
        task.setId(taskId);
        task.setAuthor(foundTask.getAuthor());
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }
}
