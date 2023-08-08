package com.ohdab.classroom.exception;

public class NoStudentException extends RuntimeException {

    public NoStudentException() {}

    public NoStudentException(String message) {
        super(message);
    }

    public NoStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoStudentException(Throwable cause) {
        super(cause);
    }
}
