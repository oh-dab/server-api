package com.ohdab.classroom.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomWorkbookUpdateDto {

    @Builder
    @Getter
    public static class Request {
        long id;
        String name;
        String description;
    }
}
