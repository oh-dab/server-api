package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomWorkbookDto;
import java.util.List;

public interface GetWorkbookListUsecase {

    List<ClassroomWorkbookDto.Response> getWorkbookListByClassroomId(long classroomId);
}
