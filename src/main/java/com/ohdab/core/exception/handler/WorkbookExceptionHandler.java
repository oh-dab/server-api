package com.ohdab.core.exception.handler;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.core.template.ErrorMessage;
import com.ohdab.workbook.exception.DuplicatedWorkbookException;
import com.ohdab.workbook.exception.InvalidWorkbookNumberRangeException;
import com.ohdab.workbook.exception.NoWorkbookException;
import com.ohdab.workbook.exception.WorkbookContentOverflowException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WorkbookExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> contentOverflowException(
            WorkbookContentOverflowException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.WORKBOOK_CONTENT_OVERFLOW.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> duplicatedWorkbookException(DuplicatedWorkbookException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.DUPLICATED_WORKBOOK.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> invalidWorkbookNumberRangeException(
            InvalidWorkbookNumberRangeException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(
                                        ExceptionEnum.INVALID_WORKBOOK_NUMBER_RANGE.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noWorkbookException(NoWorkbookException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_WORKBOOK.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }
}
