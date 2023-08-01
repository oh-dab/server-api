package com.ohdab.classroom.service.dto;

import com.ohdab.classroom.controller.request.ClassroomReq;
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

    static ClassroomDto.Request toDto(ClassroomReq req) {
        return Request.builder()
                .teacherId(req.getTeacherId())
                .info(
                        Info.builder()
                                .name(req.getName())
                                .description(req.getDescription())
                                .grade(req.getGrade())
                                .build())
                .build();
    }
}
