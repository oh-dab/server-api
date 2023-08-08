package com.ohdab.member.exception;

public class AlreadyWithdrawlException extends RuntimeException {

    public AlreadyWithdrawlException() {
    }

    public AlreadyWithdrawlException(String message) {
        super(message);
    }

    public AlreadyWithdrawlException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyWithdrawlException(Throwable cause) {
        super(cause);
    }
}
