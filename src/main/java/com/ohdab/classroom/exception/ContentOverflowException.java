package com.ohdab.classroom.exception;

public class ContentOverflowException extends RuntimeException {

    public ContentOverflowException() {}

    public ContentOverflowException(String message) {
        super(message);
    }

    public ContentOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentOverflowException(Throwable cause) {
        super(cause);
    }
}
