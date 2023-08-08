package com.ohdab.workbook.exception;

public class NoWorkbookException extends RuntimeException {

    public NoWorkbookException() {}

    public NoWorkbookException(String message) {
        super(message);
    }

    public NoWorkbookException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoWorkbookException(Throwable cause) {
        super(cause);
    }
}
