package com.ohdab.member.application.exception;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException() {}

    public DuplicatedMemberException(String message) {
        super(message);
    }

    public DuplicatedMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedMemberException(Throwable cause) {
        super(cause);
    }
}
