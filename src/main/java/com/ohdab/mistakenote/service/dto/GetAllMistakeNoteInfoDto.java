package com.ohdab.mistakenote.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllMistakeNoteInfoDto {

    @Getter
    @Builder
    public static class Response {
        private List<StudentInfo> students;
        private List<AllMistakeNoteInfoRes> allMistakeNoteInfo;

        public static class StudentInfo {}
    }

    @Getter
    @Builder
    public static class Request {
        private List<StudentRes> students;
        private List<AllMistakeNoteInfoRes> allMistakeNoteInfo;
    }
}
