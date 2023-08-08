package com.ohdab.mistakenote.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllMistakeNoteInfoRes {

    private List<StudentInfoRes> students;
    private List<AllMistakeNoteInfoRes> mistakeNoteInfo;

    @Getter
    @Builder
    public static class StudentInfoRes {

        private long studentId;
        private String name;
    }

    @Getter
    @Builder
    public static class AllMistakeNoteInfoRes {

        private int wrongNumber;
        private int wrongStudentsCount;
    }
}
