package dev.lottery.tms.mapper;


import dev.lottery.tms.dto.response.CommentResponse;
import dev.lottery.tms.dto.response.CommentsResponse;
import dev.lottery.tms.entity.Comment;
import dev.lottery.tms.entity.Task;
import dev.lottery.tms.service.TaskService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    @Lazy
    private TaskService taskService;

    @Mapping(target = "task", source = "taskId", qualifiedByName = "mapTaskFromId")
    public abstract Comment toComment(Long taskId, String text);

    @Named("mapTaskFromId")
    protected Task mapTaskFromId(Long id) {
        return taskService.getTaskEntityById(id);
    }

    public abstract CommentResponse toCommentResponse(Comment comment);

    public CommentsResponse toCommentsResponse(List<Comment> comments) {
        CommentsResponse response = new CommentsResponse();
        response.setComments(toCommentResponseList(comments));
        return response;
    }

    public List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }

        return comments.stream()
                .map(this::toCommentResponse)
                .collect(Collectors.toList());
    }
}
