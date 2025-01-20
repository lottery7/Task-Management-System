package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

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

    @Named("mapUserFromId")
    protected User mapUserFromId(Long id) {
        return userService.getUserEntityById(id);
    }
}
