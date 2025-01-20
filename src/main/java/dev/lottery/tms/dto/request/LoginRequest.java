package dev.lottery.tms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(description = "User's email", example = "coolguy@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "User's password",
            example = "coolPassword123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
