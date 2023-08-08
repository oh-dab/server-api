package com.ohdab.mistakenote.exception;

public class NoMistakeNoteException extends RuntimeException {

    public NoMistakeNoteException() {}

    public NoMistakeNoteException(String message) {
        super(message);
    }

    public NoMistakeNoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMistakeNoteException(Throwable cause) {
        super(cause);
    }
}
