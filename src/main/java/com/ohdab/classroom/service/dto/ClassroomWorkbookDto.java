package com.ohdab.classroom.service.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomWorkbookDto {

    @Getter
    @Builder
    public static class Response {
        Long id;
        String name;
        LocalDateTime createdAt;
    }
}
