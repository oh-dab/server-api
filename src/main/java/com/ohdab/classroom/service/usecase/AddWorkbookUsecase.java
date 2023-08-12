package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;

public interface AddWorkbookUsecase {

    void addWorkbook(ClassroomAddWorkbookDto.Request addWorkbookDto);
}
