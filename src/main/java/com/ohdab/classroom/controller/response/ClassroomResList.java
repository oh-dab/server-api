package com.ohdab.classroom.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClassroomResList {

    List<ClassroomRes> classroomResList;

    @Getter
    @Builder
    public static class ClassroomRes {
        private long id;
        private String name;
        private String description;
        private String grade;
    }
}
