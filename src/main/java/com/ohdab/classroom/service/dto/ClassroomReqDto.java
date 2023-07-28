package com.ohdab.classroom.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomReqDto {

    private String name;
    private String description;
    private String grade;

    private Long teacherId;
}
