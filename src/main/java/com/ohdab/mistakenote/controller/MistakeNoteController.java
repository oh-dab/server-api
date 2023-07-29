package com.ohdab.mistakenote.controller;

import com.ohdab.mistakenote.controller.mapper.MistakeNoteMapper;
import com.ohdab.mistakenote.controller.request.SaveMistakeNoteInfoReq;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoRes;
import com.ohdab.mistakenote.controller.response.SaveMistakeNoteInfoRes;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
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
    private final SaveMistakeNoteInfoUsecase saveMistakeNoteInfoUsecase;

    @GetMapping("/workbooks/{workbook-id}/students/{student-id}")
    public ResponseEntity<List<GetMistakeNoteInfoRes>> getNoteInfoByStudent(
            @Valid @PathVariable(name = "workbook-id") long workbookId,
            @PathVariable(name = "student-id") long studentId) {
        List<MistakeNoteInfoDto> mistakeNoteInfo =
                getMistakeNoteInfoUsecase.getMistakeNoteInfoByStudent(workbookId, studentId);
        return ResponseEntity.ok(
                MistakeNoteMapper.toGetMistakeNoteInfoByStudentRes(mistakeNoteInfo));
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
}
