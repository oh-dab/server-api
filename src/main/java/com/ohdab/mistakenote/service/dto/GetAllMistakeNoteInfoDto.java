package com.ohdab.mistakenote.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllMistakeNoteInfoDto {

    private List<StudentDto> students;
    private List<MistakeNoteInfoDto> mistakeNoteInfo;
}
