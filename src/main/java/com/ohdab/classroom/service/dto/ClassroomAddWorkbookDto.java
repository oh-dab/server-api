package com.ohdab.classroom.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomAddWorkbookDto {

    @Getter
    @Builder
    public static class Request {
        String name;
        String description;
        int startNumber;
        int endNumber;
    }
}
