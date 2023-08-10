package com.ohdab.classroom.exception;

public class NoGradeException extends RuntimeException {

    public NoGradeException() {}

    public NoGradeException(String message) {
        super(message);
    }

    public NoGradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoGradeException(Throwable cause) {
        super(cause);
    }
}
