package com.ohdab.classroom.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
