package com.ohdab.classroom.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteStudentDto {

    public static class Request {

        private long classroomId;
        private long studentId;
    }
}
