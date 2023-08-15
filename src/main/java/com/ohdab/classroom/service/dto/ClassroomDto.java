package com.ohdab.classroom.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomDto {

    @Getter
    @Builder
    public static class Info {
        private String name;
        private String description;
        private String grade;
    }

    @Getter
    @Builder
    public static class Request {
        private Info info;
        private long teacherId;
    }

    @Getter
    @Builder
    public static class Response {
        private long id;

        private long teacherId;

        private Info info;

        private List<Long> studentIds;

        private List<Long> workbookIds;
    }
}
