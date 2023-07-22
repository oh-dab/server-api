package com.ohdab;

import com.ohdab.dto.NoteInfoByStudentDto;
import com.ohdab.port.in.GetNoteInfoUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WrongAnswerNoteService implements GetNoteInfoUsecase {

    @Override
    public List<NoteInfoByStudentDto> getNoteInfoByStudent(Long workbookId, Long studentId) {
        return null;
    }
}
