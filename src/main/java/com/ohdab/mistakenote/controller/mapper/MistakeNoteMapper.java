package com.ohdab.mistakenote.controller.mapper;

import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.controller.response.GetAllMistakeNoteInfoRes;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoRes;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.StudentDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static GetAllMistakeNoteInfoRes toGetAllMistakeNoteInfoRes(
            GetAllMistakeNoteInfoDto getAllMistakeNoteInfoDto) {
        return GetAllMistakeNoteInfoRes.builder()
                .students(mapToStudentRes(getAllMistakeNoteInfoDto.getStudents()))
                .mistakeNoteInfo(
                        mapToMistakeNoteInfoRes(getAllMistakeNoteInfoDto.getMistakeNoteInfo()))
                .build();
    }

    private static List<StudentRes> mapToStudentRes(List<StudentDto> students) {
        return students.stream()
                .map(
                        dto ->
                                StudentRes.builder()
                                        .studentId(dto.getStudentId())
                                        .name(dto.getName())
                                        .build())
                .collect(Collectors.toList());
    }

    private static List<GetMistakeNoteInfoRes> mapToMistakeNoteInfoRes(
            List<MistakeNoteInfoDto> mistakeNoteInfo) {
        return mistakeNoteInfo.stream()
                .map(
                        dto ->
                                GetMistakeNoteInfoRes.builder()
                                        .wrongNumber(dto.getWrongNumber())
                                        .wrongCount(dto.getWrongCount())
                                        .build())
                .collect(Collectors.toList());
    }
}
