package com.ohdab.core.util.jwt.exception;

public class CustomWeakKeyException extends RuntimeException {
    public CustomWeakKeyException() {}

    public CustomWeakKeyException(String message) {
        super(message);
    }

    public CustomWeakKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomWeakKeyException(Throwable cause) {
        super(cause);
    }
}
