package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object representing a comment")
public class CommentResponse {
    @Schema(description = "Unique identifier of the comment",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Text content of the comment",
            example = "This is a sample comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

    @Schema(description = "User who created the comment",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private UserResponse user;
}
