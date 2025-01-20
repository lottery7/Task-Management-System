package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.dto.response.TasksResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    @Lazy
    private UserService userService;

    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUserFromId")
    public abstract Task toTask(CreateTaskRequest createTaskRequest);

    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUserFromId")
    public abstract Task toTask(UpdateTaskRequest updateTaskRequest);

    public abstract TaskResponse toTaskResponse(Task task);

    public TasksResponse toTasksResponse(List<Task> tasks) {
        TasksResponse response = new TasksResponse();
        response.setTasks(toTaskResponseList(tasks));
        return response;
    }

    public List<TaskResponse> toTaskResponseList(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }

        return tasks.stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    @Named("mapUserFromId")
    protected User mapUserFromId(Long id) {
        return userService.getUserEntityById(id);
    }
}
