package com.ohdab.port.in;

import com.ohdab.dto.NoteInfoByStudentDto;
import java.util.List;

public interface GetNoteInfoUsecase {

    List<NoteInfoByStudentDto> getNoteInfoByStudent(Long workbookId, Long studentId);
}
