package com.ohdab.mistakenote.controller.mapper;

import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoRes;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import java.util.List;
import java.util.stream.Collectors;

public class MistakeNoteMapper {

    public static List<GetMistakeNoteInfoRes> toGetMistakeNoteInfoByStudentRes(
            List<MistakeNoteInfoDto> noteInfoByStudentDtoList) {
        return noteInfoByStudentDtoList.stream()
                .map(
                        dto ->
                                GetMistakeNoteInfoRes.builder()
                                        .wrongNumber(dto.getWrongNumber())
                                        .wrongCount(dto.getWrongCount())
                                        .build())
                .collect(Collectors.toList());
    }

    public static SaveMistakeNoteInfoDto toSaveMistakeNoteInfoDto(
            long workbookId, long studentId, SaveMistakeNoteInfoReq saveMistakeNoteInfoReq) {
        return SaveMistakeNoteInfoDto.builder()
                .workbookId(workbookId)
                .studentId(studentId)
                .mistakeNumbers(saveMistakeNoteInfoReq.getMistakeNumbers())
                .build();
    }
}
