package com.ohdab.classroom.service.dto;

import com.ohdab.classroom.domain.classroomInfo.Grade;
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
