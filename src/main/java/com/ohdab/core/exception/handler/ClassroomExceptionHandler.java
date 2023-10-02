package com.ohdab.core.exception.handler;

import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.exception.NoGradeException;
import com.ohdab.classroom.exception.NoStudentException;
import com.ohdab.classroom.exception.NoTeacherException;
import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.core.template.ErrorMessage;
import com.ohdab.mistakenote.exception.MistakeNoteIsEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.ohdab.classroom"})
public class ClassroomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noClassroomException(NoClassroomException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_CLASSROOM.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noGradeException(NoGradeException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_GRADE.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noStudentException(NoStudentException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_STUDENT.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noTeacherException(NoTeacherException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.NO_TEACHER.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> mistakeNoteIsEmptyException(MistakeNoteIsEmptyException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(ExceptionEnum.MISTAKE_NOTE_IS_EMPTY.getErrorCode())
                                .message(e.getMessage())
                                .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorMessage.builder()
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .build());
    }
}
