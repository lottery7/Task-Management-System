package dev.lottery.tms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        timestamp = LocalDateTime.now();
    }
}
