package com.ohdab.classroom.exception;

public class NoClassroomException extends RuntimeException {

    public NoClassroomException() {}

    public NoClassroomException(String message) {
        super(message);
    }

    public NoClassroomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoClassroomException(Throwable cause) {
        super(cause);
    }
}
