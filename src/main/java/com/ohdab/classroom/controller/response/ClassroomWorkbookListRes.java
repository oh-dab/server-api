package com.ohdab.classroom.controller.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClassroomWorkbookListRes {

    private List<WorkbookInfo> workbookList;

    @Getter
    @Builder
    public static class WorkbookInfo {
        long id;
        String name;
        LocalDate createdAt;
    }
}
