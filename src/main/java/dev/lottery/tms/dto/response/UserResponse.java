package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response object representing a user")
public class UserResponse {
    @Schema(description = "Unique identifier of the user", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Name of the user", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Email address of the user", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
