package com.ohdab.mistakenote.exception;

public class MistakeNoteIsEmptyException extends RuntimeException {

    public MistakeNoteIsEmptyException() {}

    public MistakeNoteIsEmptyException(String message) {
        super(message);
    }

    public MistakeNoteIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MistakeNoteIsEmptyException(Throwable cause) {
        super(cause);
    }
}
