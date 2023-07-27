package com.ohdab.mistakenote.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MistakeNoteInfoDto {

    private int wrongNumber;
    private int wrongCount;
}
