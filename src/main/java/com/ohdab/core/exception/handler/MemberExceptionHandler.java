package com.ohdab.core.exception.handler;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.core.template.ErrorMessage;
import com.ohdab.member.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> alreadyWithdrawException(AlreadyWithdrawlException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.ALREADY_WITHDRAWL.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> duplicatedMemberException(DuplicatedMemberException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.DUPLICATED_MEMBER.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noMemberException(NoMemberException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_MEMBER.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> contentOverflowException(MemberContentOverflowException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.MEMBER_CONTENT_OVERFLOW.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noAuthorityException(NoAuthorityException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_AUTHORITY.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }
}
