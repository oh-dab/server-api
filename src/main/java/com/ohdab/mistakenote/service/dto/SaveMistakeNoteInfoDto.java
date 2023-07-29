package com.ohdab.mistakenote.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveMistakeNoteInfoDto {

    private long workbookId;
    private long studentId;
    private int[] mistakeNumbers;
}
