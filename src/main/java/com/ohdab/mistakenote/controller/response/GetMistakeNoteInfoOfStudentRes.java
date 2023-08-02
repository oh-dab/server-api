package com.ohdab.mistakenote.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMistakeNoteInfoOfStudentRes {

    private int wrongNumber;
    private int wrongCount;
}
