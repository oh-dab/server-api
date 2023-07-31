package com.ohdab.mistakenote.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentDto {

    private long studentId;
    private String name;
}
