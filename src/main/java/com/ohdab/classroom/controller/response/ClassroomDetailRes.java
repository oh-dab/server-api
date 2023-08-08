package com.ohdab.classroom.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomDetailRes {

    private long id;

    private String name;
    private String description;
    private String grade;

    private long teacherId;
    private List<Long> studentIds;
    private List<Long> workbookIds;
}
