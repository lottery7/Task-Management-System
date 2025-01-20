package dev.lottery.tms.dto.response;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private UserResponse user;
}
