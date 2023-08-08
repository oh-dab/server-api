package com.ohdab.mistakenote.exception;

public class NumberIsOutOfRangeException extends RuntimeException {

    public NumberIsOutOfRangeException() {}

    public NumberIsOutOfRangeException(String message) {
        super(message);
    }

    public NumberIsOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumberIsOutOfRangeException(Throwable cause) {
        super(cause);
    }
}
