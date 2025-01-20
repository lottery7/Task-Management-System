package dev.lottery.tms.service;

import dev.lottery.tms.dto.request.GetCommentsRequest;
import dev.lottery.tms.dto.response.CommentsResponse;
import dev.lottery.tms.entity.Comment;
import dev.lottery.tms.mapper.CommentMapper;
import dev.lottery.tms.respository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentsResponse getComments(GetCommentsRequest request) {
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy()));
        Page<Comment> foundComments;

        Long taskId = request.getTaskId();
        Long userId = request.getAuthorId();

        if (taskId != null && userId != null) {
            foundComments = commentRepository.findByTaskIdAndUserId(taskId, userId, pageable);
        } else if (taskId != null) {
            foundComments = commentRepository.findByTaskId(taskId, pageable);
        } else if (userId != null) {
            foundComments = commentRepository.findByUserId(userId, pageable);
        } else {
            foundComments = commentRepository.findAll(pageable);
        }

        return commentMapper.toCommentsResponse(foundComments.getContent());
    }

    public CommentsResponse getComments(Long taskId, int page, int size) {
        GetCommentsRequest request = new GetCommentsRequest();
        request.setTaskId(taskId);
        request.setPage(page);
        request.setSize(size);
        request.setSortBy("id");

        return getComments(request);
    }
}
