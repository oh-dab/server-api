package com.ohdab.classroom.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomDetailRes {

    private long id;

    private String name;
    private String description;
    private String grade;

    private long teacherId;
    private List<StudentInfo> studentInfoList;
    private List<WorkbookInfo> workbookInfoList;

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
