package dev.lottery.tms.exception;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException() {
        super("Not authenticated");
    }
}
