package com.ohdab.classroom.exception;

public class CannotFindGradeException extends RuntimeException {

    public CannotFindGradeException(String message, Exception e) {
        super(message);
    }
}
