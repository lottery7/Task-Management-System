package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response containing message")
public class MessageResponse {
    @Schema(
            description = "Message content",
            example = "Success",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;
}
