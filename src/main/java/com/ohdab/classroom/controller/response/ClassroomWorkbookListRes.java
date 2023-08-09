package com.ohdab.classroom.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClassroomWorkbookListRes {

    @JsonProperty("workbooks")
    List<WorkbookInfo> workbookList;

    @Getter
    @Builder
    public static class WorkbookInfo {
        long id;
        String name;

        @JsonProperty("created_at")
        LocalDate createdAt;
    }
}
