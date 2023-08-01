package com.ohdab.mistakenote.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllMistakeNoteInfoDto {

    private int wrongNumber;
    private int wrongStudentsCount;
}
