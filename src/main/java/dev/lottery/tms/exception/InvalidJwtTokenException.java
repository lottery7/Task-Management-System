package dev.lottery.tms.exception;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException() {
        super("Invalid JWT token");
    }
}
