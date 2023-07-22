package com.ohdab.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoteInfoByStudentRes {

    private int wrongNumber;
    private int wrongCount;
}
