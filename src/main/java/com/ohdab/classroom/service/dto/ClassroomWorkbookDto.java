package com.ohdab.classroom.service.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClassroomWorkbookDto {

    @Getter
    @Builder
    public static class Response {
        Long id;
        String name;
        LocalDateTime createdAt;
    }
}
