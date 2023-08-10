package com.ohdab.classroom.exception;

public class NoGradeException extends RuntimeException {

    public NoGradeException(String message, Exception e) {
        super(message);
    }
}
