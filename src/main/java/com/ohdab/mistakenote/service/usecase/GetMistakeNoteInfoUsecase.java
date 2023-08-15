package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;

public interface GetMistakeNoteInfoUsecase {

    GetMistakeNoteInfoOfStudentDto.Response getMistakeNoteInfoOfStudent(
            long workbookId, long studentId);

    GetAllMistakeNoteInfoDto.Response getAllMistakeNoteInfo(long workbookId);
}
