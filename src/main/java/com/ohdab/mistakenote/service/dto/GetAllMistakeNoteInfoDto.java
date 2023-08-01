package com.ohdab.mistakenote.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAllMistakeNoteInfoDto {

    @Getter
    @Builder
    public static class Response {
        private List<StudentInfoDto> students;
        private List<AllMistakeNoteInfoDto> allMistakeNoteInfo;

        @Getter
        @Builder
        public static class StudentInfoDto {
            private long studentId;
            private String name;
        }

        @Getter
        @Builder
        public static class AllMistakeNoteInfoDto {
            private int wrongNumber;
            private int wrongStudentsCount;
        }
    }
}
