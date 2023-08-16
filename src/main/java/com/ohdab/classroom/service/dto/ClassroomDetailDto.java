package com.ohdab.classroom.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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

        private List<StudentInfo> studentInfoList;

        private List<WorkbookInfo> workbookInfoList;
    }

    @Getter
    @Builder
    public static class StudentInfo {

        private long studentId;
        private String studentName;
    }

    @Getter
    @Builder
    public static class WorkbookInfo {

        private long workbookId;
        private String workbookName;
    }
}
