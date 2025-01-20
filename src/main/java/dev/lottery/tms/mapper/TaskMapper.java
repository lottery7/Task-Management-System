package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.request.CreateTaskRequest;
import dev.lottery.tms.dto.request.UpdateTaskRequest;
import dev.lottery.tms.dto.response.TaskResponse;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.exception.UserNotFoundException;
import dev.lottery.tms.respository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUserFromId")
    public abstract Task toTask(CreateTaskRequest createTaskRequest);

    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "mapUserFromId")
    public abstract Task toTask(UpdateTaskRequest updateTaskRequest);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    public abstract TaskResponse toTaskResponse(Task task);

    @Named("mapUserFromId")
    protected User mapUserFromId(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
