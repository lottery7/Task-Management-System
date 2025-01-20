package dev.lottery.tms.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CommentsResponse {
    List<CommentResponse> comments;
}
