package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomWorkbookUpdateDto;

public interface UpdateWorkbookInfoUsecase {

    void updateWorkbookInfo(ClassroomWorkbookUpdateDto.Request updateWorkbookReq);
}
