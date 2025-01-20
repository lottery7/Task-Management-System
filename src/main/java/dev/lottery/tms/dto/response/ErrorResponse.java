package dev.lottery.tms.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Date timestamp;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        timestamp = new Date();
    }
}
