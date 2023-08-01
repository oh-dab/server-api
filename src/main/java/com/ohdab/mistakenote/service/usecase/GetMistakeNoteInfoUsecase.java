package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudent;

public interface GetMistakeNoteInfoUsecase {

    GetMistakeNoteInfoOfStudent.Response getMistakeNoteInfoOfStudent(
            long workbookId, long studentId);

    GetAllMistakeNoteInfoDto.Response getAllMistakeNoteInfo(long workbookId);
}
