package com.ohdab.classroom.exception;

public class NoTeacherException extends RuntimeException {

    public NoTeacherException() {}

    public NoTeacherException(String message) {
        super(message);
    }

    public NoTeacherException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTeacherException(Throwable cause) {
        super(cause);
    }
}
