package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Response object containing a list of comments")
public class CommentsResponse {
    @Schema(description = "List of comments", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CommentResponse> comments;
}