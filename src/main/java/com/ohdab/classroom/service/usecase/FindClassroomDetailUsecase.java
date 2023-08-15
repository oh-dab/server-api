package com.ohdab.classroom.service.usecase;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;

public interface FindClassroomDetailUsecase {

    ClassroomDetailDtoResponse getClassroomDetailById(long classroomId);
}
