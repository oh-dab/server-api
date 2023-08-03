package com.ohdab.mistakenote.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetNumbersWrongNTimeDto {

    @Getter
    @Builder
    public static class Request {

        private long mistakeNoteId;
        private int count;
        private int from;
        private int to;
    }

    @Getter
    @Builder
    public static class Response {

        private String wrongNumbers;
    }
}
