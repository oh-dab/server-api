package com.ohdab.core.exception.handler;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.core.template.ErrorMessage;
import com.ohdab.mistakenote.exception.MistakeNumbersSizeException;
import com.ohdab.mistakenote.exception.NoMistakeNoteException;
import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.exception.NumberIsOutOfRangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MistakeNoteExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noMistakeException(NoMistakeNoteException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_MISTAKE_NOTE.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noNumbersWrongNTimesException(
            NoNumbersWrongNTimesException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_NUMBERS_WRONG_N_TIMES.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> numberIsOutOfRangeException(NumberIsOutOfRangeException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NUMBER_IS_OUT_OF_RANGE.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> mistakeNumberSizeException(MistakeNumbersSizeException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.MISTAKE_NUMBERS_SIZE.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }
}
