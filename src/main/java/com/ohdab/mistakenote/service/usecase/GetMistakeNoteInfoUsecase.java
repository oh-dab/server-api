package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;
import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimeDto;

public interface GetMistakeNoteInfoUsecase {

    GetMistakeNoteInfoOfStudentDto.Response getMistakeNoteInfoOfStudent(
            long workbookId, long studentId);

    GetNumbersWrongNTimeDto.Response getNumbersWrongNTimes(
            GetNumbersWrongNTimeDto.Request getNumbersWrongNTimeDto);

    GetAllMistakeNoteInfoDto.Response getAllMistakeNoteInfo(long workbookId);
}
