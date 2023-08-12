package com.ohdab.classroom.service;

import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto.Request;
import com.ohdab.classroom.service.usecase.AddWorkbookUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddWorkbookService implements AddWorkbookUsecase {

    @Override
    public void addWorkbook(Request addWorkbookDto) {}
}
