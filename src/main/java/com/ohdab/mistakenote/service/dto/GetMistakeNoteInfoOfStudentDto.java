package com.ohdab.mistakenote.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMistakeNoteInfoOfStudentDto {

    @Getter
    @Builder
    public static class Response {
        private List<MistakeNoteInfoDto> mistakeNoteInfo;

        @Getter
        @Builder
        public static class MistakeNoteInfoDto {
            private int wrongNumber;
            private int wrongCount;
        }
    }
}
