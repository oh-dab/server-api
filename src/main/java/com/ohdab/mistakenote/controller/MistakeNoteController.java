package com.ohdab.mistakenote.controller;

import com.ohdab.mistakenote.controller.mapper.MistakeNoteMapper;
import com.ohdab.mistakenote.controller.response.GetMistakeNoteInfoRes;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mistake-notes")
public class MistakeNoteController {

    private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;

    @GetMapping("/workbooks/{workbook-id}/students/{student-id}")
    public ResponseEntity<List<GetMistakeNoteInfoRes>> getNoteInfoByStudent(
            @Valid @PathVariable(name = "workbook-id") long workbookId,
            @PathVariable(name = "student-id") long studentId) {
        List<MistakeNoteInfoDto> mistakeNoteInfo =
                getMistakeNoteInfoUsecase.getMistakeNoteInfoByStudent(workbookId, studentId);
        return ResponseEntity.ok(
                MistakeNoteMapper.toGetMistakeNoteInfoByStudentRes(mistakeNoteInfo));
    }
}
