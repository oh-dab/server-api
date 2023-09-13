package com.ohdab.mistakenote.exception;

public class NoNumbersWrongNTimesException extends RuntimeException {

    public NoNumbersWrongNTimesException() {}

    public NoNumbersWrongNTimesException(String message) {
        super(message);
    }

    public NoNumbersWrongNTimesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNumbersWrongNTimesException(Throwable cause) {
        super(cause);
    }
}
