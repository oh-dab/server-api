package com.ohdab.core.exception.handler;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.core.template.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.IS_NULL.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }
}
