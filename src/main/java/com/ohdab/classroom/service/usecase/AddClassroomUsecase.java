package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomDto;

public interface AddClassroomUsecase {

    void addClassroom(ClassroomDto.Request classroomReqDto);
}
