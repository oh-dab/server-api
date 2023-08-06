package com.ohdab.classroom.exception;

public class CannotFindClassroomException extends RuntimeException {

    public CannotFindClassroomException(String message, Exception e) {
        super(message);
    }
}
