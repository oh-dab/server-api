package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SaveMistakeNoteInfoService.class})
class SaveMistakeNoteInfoServiceTest {

    @Autowired
    private SaveMistakeNoteInfoUsecase saveMistakeNoteInfoUsecase;

}