package com.ohdab.classroom.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomDetailDto {

    @Getter
    @Builder
    public static class ClassroomDetailInfo {
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

        private ClassroomDetailInfo info;

        private List<StudentInfoDto> studentInfoDtoList;

        private List<WorkbookInfoDto> workbookInfoDtoList;
    }

    @Getter
    @Builder
    public static class StudentInfoDto {

        private long studentId;
        private String studentName;
    }

    @Getter
    @Builder
    public static class WorkbookInfoDto {

        private long workbookId;
        private String workbookName;
    }
}
