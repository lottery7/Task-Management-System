package dev.lottery.tms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterRequest {
    @Schema(description = "User's name",
            example = "John Doe",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "User's email address",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "User's password",
            example = "securePassword123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
