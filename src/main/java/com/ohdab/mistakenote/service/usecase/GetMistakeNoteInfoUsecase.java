package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import java.util.List;

public interface GetMistakeNoteInfoUsecase {

    List<MistakeNoteInfoDto> getMistakeNoteInfoByStudent(long workbookId, long studentId);

    GetAllMistakeNoteInfoDto getAllMistakeNoteInfo(long workbookId);
}
