package com.ohdab.member.exception;

public class MemberContentOverflowException extends RuntimeException {

    public MemberContentOverflowException() {}

    public MemberContentOverflowException(String message) {
        super(message);
    }

    public MemberContentOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberContentOverflowException(Throwable cause) {
        super(cause);
    }
}
