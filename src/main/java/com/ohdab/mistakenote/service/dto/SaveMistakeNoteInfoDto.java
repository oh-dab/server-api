package com.ohdab.mistakenote.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SaveMistakeNoteInfoDto {

    private long workbookId;
    private long studentId;
    private List<Integer> mistakeNumbers;
}
