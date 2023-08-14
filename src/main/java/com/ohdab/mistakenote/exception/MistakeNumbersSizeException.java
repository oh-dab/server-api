package com.ohdab.mistakenote.exception;

public class MistakeNumbersSizeException extends RuntimeException {

    public MistakeNumbersSizeException() {}

    public MistakeNumbersSizeException(String message) {
        super(message);
    }

    public MistakeNumbersSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MistakeNumbersSizeException(Throwable cause) {
        super(cause);
    }
}
