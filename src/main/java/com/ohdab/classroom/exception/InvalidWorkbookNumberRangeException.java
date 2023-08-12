package com.ohdab.classroom.exception;

public class InvalidWorkbookNumberRangeException extends RuntimeException {

    public InvalidWorkbookNumberRangeException() {}

    public InvalidWorkbookNumberRangeException(String message) {
        super(message);
    }

    public InvalidWorkbookNumberRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWorkbookNumberRangeException(Throwable cause) {
        super(cause);
    }
}
