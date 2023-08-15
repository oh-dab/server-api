package com.ohdab.workbook.exception;

public class DuplicatedWorkbookException extends RuntimeException {

    public DuplicatedWorkbookException() {}

    public DuplicatedWorkbookException(String message) {
        super(message);
    }

    public DuplicatedWorkbookException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedWorkbookException(Throwable cause) {
        super(cause);
    }
}
