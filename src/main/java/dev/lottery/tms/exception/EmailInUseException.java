package dev.lottery.tms.exception;

public class EmailInUseException extends RuntimeException {
    public EmailInUseException() {
        super("Email already in use");
    }
}
