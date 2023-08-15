package com.ohdab.workbook.exception;

public class WorkbookContentOverflowException extends RuntimeException {

    public WorkbookContentOverflowException() {}

    public WorkbookContentOverflowException(String message) {
        super(message);
    }

    public WorkbookContentOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkbookContentOverflowException(Throwable cause) {
        super(cause);
    }
}
