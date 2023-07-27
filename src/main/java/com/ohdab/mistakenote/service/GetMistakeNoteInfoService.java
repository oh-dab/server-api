package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMistakeNoteInfoService implements GetMistakeNoteInfoUsecase {

    @Override
    public List<MistakeNoteInfoDto> getMistakeNoteInfoByStudent(long workbookId, long studentId) {
        return null;
    }
}
