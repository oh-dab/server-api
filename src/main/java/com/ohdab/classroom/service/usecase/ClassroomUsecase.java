package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomDto;

public interface ClassroomUsecase {

    void addClassroom(ClassroomDto.Request classroomReqDto);
}
