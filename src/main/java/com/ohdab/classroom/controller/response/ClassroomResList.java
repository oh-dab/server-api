package com.ohdab.classroom.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClassroomResList {

    List<ClassroomInfo> classroomInfoList;

    @Getter
    @Builder
    public static class ClassroomInfo {
        private long id;
        private String name;
        private String description;
        private String grade;
    }
}
