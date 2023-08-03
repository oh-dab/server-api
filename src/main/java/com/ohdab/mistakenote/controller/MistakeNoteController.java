package com.ohdab.mistakenote.controller;

import com.ohdab.mistakenote.controller.mapper.MistakeNoteMapper;
import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.controller.response.GetAllMistakeNoteInfoRes;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoOfStudentRes;
import com.ohdab.mistakenote.controller.response.GetNumberWrongNTimes;
import com.ohdab.mistakenote.controller.response.SaveMistakeNoteInfoRes;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;
import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mistake-notes")
public class MistakeNoteController {

    private final GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;
    private final GetNumbersWrongNTimesUsecase getNumbersWrongNTimesUsecase;
    private final SaveMistakeNoteInfoUsecase saveMistakeNoteInfoUsecase;

    @GetMapping("/workbooks/{workbook-id}/students/{student-id}")
    public ResponseEntity<List<GetMistakeNoteInfoOfStudentRes>> getMistakeNoteInfoOfStudent(
            @PathVariable(name = "workbook-id") long workbookId,
            @PathVariable(name = "student-id") long studentId) {
        GetMistakeNoteInfoOfStudentDto.Response mistakeNoteInfo =
                getMistakeNoteInfoUsecase.getMistakeNoteInfoOfStudent(workbookId, studentId);
        return ResponseEntity.ok(
                MistakeNoteMapper.toGetMistakeNoteInfoOfStudentRes(mistakeNoteInfo));
    }

    @GetMapping("/workbooks/{workbook-id}/{mistake-note-id}")
    public ResponseEntity<GetNumberWrongNTimes> getNumberWrongNTimes(
            @PathVariable(name = "workbook-id") long workbookId,
            @PathVariable(name = "mistake-note-id") long mistakeNoteId,
            @RequestParam(name = "count") int count,
            @RequestParam(name = "from") int from,
            @RequestParam(name = "to") int to) {
        GetNumberWrongNTimesDto.Response numberWrongNTimes =
                getNumbersWrongNTimesUsecase.getNumberWrongNTimes(
                        MistakeNoteMapper.toGetNumberWrongNTimeDto(
                                workbookId, mistakeNoteId, count, from, to));
        return ResponseEntity.ok(MistakeNoteMapper.toGetNumberWrongNTimesRes(numberWrongNTimes));
    }

    @PostMapping("/workbooks/{workbook-id}/students/{student-id}")
    public ResponseEntity<SaveMistakeNoteInfoRes> saveMistakeNoteInfo(
            @PathVariable(name = "workbook-id") long workbookId,
            @PathVariable(name = "student-id") long studentId,
            @Valid @RequestBody SaveMistakeNoteInfoReq saveMistakeNoteInfoReq) {
        saveMistakeNoteInfoUsecase.saveMistakeNoteInfo(
                MistakeNoteMapper.toSaveMistakeNoteInfoDto(
                        workbookId, studentId, saveMistakeNoteInfoReq));
        return ResponseEntity.ok(SaveMistakeNoteInfoRes.builder().message("오답이 기록되었습니다.").build());
    }

    @GetMapping("/workbooks/{workbook-id}")
    public ResponseEntity<GetAllMistakeNoteInfoRes> getAllMistakeNoteInfo(
            @PathVariable(name = "workbook-id") long workbookId) {
        GetAllMistakeNoteInfoDto.Response getAllMistakeNoteInfoDto =
                getMistakeNoteInfoUsecase.getAllMistakeNoteInfo(workbookId);
        return ResponseEntity.ok(
                MistakeNoteMapper.toGetAllMistakeNoteInfoRes(getAllMistakeNoteInfoDto));
    }
}
