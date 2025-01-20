package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.*;
import dev.lottery.tms.dto.response.CommentResponse;
import dev.lottery.tms.dto.response.CommentsResponse;
import dev.lottery.tms.dto.response.MessageResponse;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.entity.Comment;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.TaskNotFoundException;
import dev.lottery.tms.mapper.CommentMapper;
import dev.lottery.tms.mapper.TaskMapper;
import dev.lottery.tms.respository.CommentRepository;
import dev.lottery.tms.respository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public TaskResponse createNewTask(CreateTaskRequest request) {
        User author = userService.getCurrentUserEntity();

        Task task = taskMapper.toTask(request);
        task.setAuthor(author);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toTaskResponse(savedTask);
    }

    public TaskResponse getTaskDtoById(Long taskId) {
        Task task = getTaskEntityById(taskId);
        return taskMapper.toTaskResponse(task);
    }

    public Task getTaskEntityById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) {
        Task foundTask = getTaskEntityById(taskId);
        Task task = taskMapper.toTask(request);
        task.setId(taskId);
        task.setAuthor(foundTask.getAuthor());
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }

    public TaskResponse updateTaskStatus(Long taskId, UpdateTaskStatusRequest request) {
        Task foundTask = getTaskEntityById(taskId);
        foundTask.setStatus(request.getStatus());

        Task savedTask = taskRepository.save(foundTask);

        return taskMapper.toTaskResponse(savedTask);
    }

    public TaskResponse updateTaskPriority(Long taskId, UpdateTaskPriorityRequest request) {
        Task foundTask = getTaskEntityById(taskId);
        foundTask.setPriority(request.getPriority());

        Task savedTask = taskRepository.save(foundTask);

        return taskMapper.toTaskResponse(savedTask);
    }

    public MessageResponse deleteTask(Long taskId) {
        taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(taskId);
        return new MessageResponse("Deleted successfully");
    }

    public CommentResponse addComment(Long taskId, CreateCommentRequest request) {
        Comment comment = commentMapper.toComment(taskId, request);
        comment.setUser(userService.getCurrentUserEntity());
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponse(savedComment);
    }

    public CommentsResponse getComments(Long taskId) {
        Task task = getTaskEntityById(taskId);
        return commentMapper.toCommentsResponse(task.getComments());
    }

    public boolean isAssignee(Long taskId) {
        Task task = getTaskEntityById(taskId);
        String email = userService.getCurrentUserEmail();
        return task.getAssignee().getEmail().equals(email);
    }

    public TaskResponse updateAssignee(Long taskId, UpdateAssigneeRequest request) {
        Task task = getTaskEntityById(taskId);
        User newAssignee = userService.getUserEntityById(request.getAssigneeId());
        task.setAssignee(newAssignee);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponse(savedTask);
    }
}
