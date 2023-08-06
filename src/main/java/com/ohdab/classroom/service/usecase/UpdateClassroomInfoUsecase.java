package com.ohdab.classroom.service.usecase;

import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;

public interface UpdateClassroomInfoUsecase {

    void updateClassroomInfo(ClassroomUpdateDtoRequest request);
}
