package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomWorkbookListDto;
import java.util.List;

public interface GetWorkbookListUsecase {

    List<ClassroomWorkbookListDto.Response> getWorkbookListByClassroomId(long classroomId);
}
