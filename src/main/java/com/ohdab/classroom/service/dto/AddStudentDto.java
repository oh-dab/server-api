package com.ohdab.classroom.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddStudentDto {

    @Getter
    @Builder
    public static class Request {

        private long classroomId;
        private String studentName;
    }
}
