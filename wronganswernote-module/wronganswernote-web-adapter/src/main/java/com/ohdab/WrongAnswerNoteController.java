package com.ohdab;

import com.ohdab.dto.NoteInfoByStudentDto;
import com.ohdab.mapper.WrongAnswerNoteMapper;
import com.ohdab.port.in.GetNoteInfoUsecase;
import com.ohdab.response.NoteInfoByStudentRes;
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
@RequestMapping("/notes")
public class WrongAnswerNoteController {

    private final GetNoteInfoUsecase getNoteInfoUsecase;

    @GetMapping("/workbooks/{workbook-id}/students/{student-id}")
    public ResponseEntity<List<NoteInfoByStudentRes>> getNoteInfoByStudent(
            @Valid @PathVariable(name = "workbook-id") Long workbookId,
            @PathVariable(name = "student-id") Long studentId) {
        List<NoteInfoByStudentDto> noteInfoByStudentDtoList =
                getNoteInfoUsecase.getNoteInfoByStudent(workbookId, studentId);
        return ResponseEntity.ok(
                WrongAnswerNoteMapper.toNoteInfoByStudentRes(noteInfoByStudentDtoList));
    }
}
