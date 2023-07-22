package com.ohdab.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoteInfoByStudentDto {

    private int wrongNumber;
    private int wrongCount;
}
