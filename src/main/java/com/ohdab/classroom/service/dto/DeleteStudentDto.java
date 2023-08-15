package com.ohdab.classroom.service.dto;

import lombok.Builder;
import lombok.Getter;

public class DeleteStudentDto {

    @Getter
    @Builder
    public static class Request {

        private long classroomId;
        private long studentId;
    }
}
