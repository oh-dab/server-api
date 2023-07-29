package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveMistakeNoteInfoService implements SaveMistakeNoteInfoUsecase {

    @Override
    public void saveMistakeNoteInfo(SaveMistakeNoteInfoDto saveMistakeNoteInfoDto) {
        // TODO : service 구현 예정
    }
}
