package dev.lottery.tms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Error response containing details about the error")
public class ErrorResponse {

    @Schema(
            description = "HTTP status code of the error",
            example = "400",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int statusCode;

    @Schema(
            description = "Human-readable message describing the error",
            example = "Invalid input provided",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2023-10-01T12:34:56.789Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date timestamp;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        timestamp = new Date();
    }
}
