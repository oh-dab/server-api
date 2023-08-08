package com.ohdab.classroom.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomDetailDto {

    @Getter
    @Builder
    public static class ClassroomDetailDtoInfo {
        private String name;
        private String description;
        private String grade;
    }

    @Getter
    @Builder
    public static class ClassroomDetailDtoRequest {
        private long classroomId;
    }

    @Getter
    @Builder
    public static class ClassroomDetailDtoResponse {
        private long classroomId;

        private long teacherId;

        private ClassroomDetailDtoInfo info;

        private List<Long> studentIds;

        private List<Long> workbookIds;
    }
}
