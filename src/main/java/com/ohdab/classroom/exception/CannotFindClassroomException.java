package com.ohdab.classroom.exception;

public class CannotFindClassroomException extends RuntimeException {

    public CannotFindClassroomException() {}

    public CannotFindClassroomException(String message) {
        super(message);
    }

    public CannotFindClassroomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindClassroomException(Throwable cause) {
        super(cause);
    }
}
