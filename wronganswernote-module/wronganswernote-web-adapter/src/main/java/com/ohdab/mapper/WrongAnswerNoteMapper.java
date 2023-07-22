package com.ohdab.mapper;

import com.ohdab.dto.NoteInfoByStudentDto;
import com.ohdab.response.NoteInfoByStudentRes;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongAnswerNoteMapper {

    public static List<NoteInfoByStudentRes> toNoteInfoByStudentRes(
            List<NoteInfoByStudentDto> noteInfoByStudentDtoList) {
        return noteInfoByStudentDtoList.stream()
                .map(
                        dto ->
                                NoteInfoByStudentRes.builder()
                                        .wrongNumber(dto.getWrongNumber())
                                        .wrongCount(dto.getWrongCount())
                                        .build())
                .collect(Collectors.toList());
    }
}
