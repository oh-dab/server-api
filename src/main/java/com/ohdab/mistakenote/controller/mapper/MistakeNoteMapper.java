package com.ohdab.mistakenote.controller.mapper;

import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.controller.response.GetAllMistakeNoteInfoRes;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoOfStudentRes;
import com.ohdab.mistakenote.controller.response.GetNumbersWrongNTimes;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;
import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MistakeNoteMapper {

    public static List<GetMistakeNoteInfoOfStudentRes> toGetMistakeNoteInfoOfStudentRes(
            GetMistakeNoteInfoOfStudentDto.Response responseDto) {
        return responseDto.getMistakeNoteInfo().stream()
                .map(
                        dto ->
                                GetMistakeNoteInfoOfStudentRes.builder()
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
            GetAllMistakeNoteInfoDto.Response responseDto) {
        return GetAllMistakeNoteInfoRes.builder()
                .students(mapToStudentInfoRes(responseDto.getStudents()))
                .mistakeNoteInfo(
                        mapToMistakeNoteInfoOfStudentRes(responseDto.getAllMistakeNoteInfo()))
                .build();
    }

    private static List<GetAllMistakeNoteInfoRes.StudentInfoRes> mapToStudentInfoRes(
            List<StudentInfoDto> students) {
        return students.stream()
                .map(
                        dto ->
                                GetAllMistakeNoteInfoRes.StudentInfoRes.builder()
                                        .studentId(dto.getStudentId())
                                        .name(dto.getName())
                                        .build())
                .collect(Collectors.toList());
    }

    private static List<GetAllMistakeNoteInfoRes.AllMistakeNoteInfoRes>
            mapToMistakeNoteInfoOfStudentRes(
                    List<GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto> mistakeNoteInfo) {
        return mistakeNoteInfo.stream()
                .map(
                        dto ->
                                GetAllMistakeNoteInfoRes.AllMistakeNoteInfoRes.builder()
                                        .wrongNumber(dto.getWrongNumber())
                                        .wrongStudentsCount(dto.getWrongStudentsCount())
                                        .build())
                .collect(Collectors.toList());
    }

    public static GetNumbersWrongNTimesDto.Request toGetNumbersWrongNTimeDto(
            long workbookId, long mistakeNoteId, int count, int from, int to) {
        return GetNumbersWrongNTimesDto.Request.builder()
                .workbookId(workbookId)
                .mistakeNoteId(mistakeNoteId)
                .count(count)
                .from(from)
                .to(to)
                .build();
    }

    public static GetNumbersWrongNTimes toGetNumbersWrongNTimesRes(
            GetNumbersWrongNTimesDto.Response numbersWrongNTimes) {
        return GetNumbersWrongNTimes.builder()
                .wrongNumbers(numbersWrongNTimes.getWrongNumbers())
                .build();
    }
}
