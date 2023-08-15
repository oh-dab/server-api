package com.ohdab.classroom.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomUpdateDto {

    @Getter
    @Builder
    public static class ClassroomUpdateDtoRequest {
        private long classroomId;

        private String name;
        private String description;
        private String grade;
    }
}
