package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.ClassroomDto;
import java.util.List;

public interface FindClassroomListUsecase {

    List<ClassroomDto.Response> findClassroomListByTeacherId(long teacherId);
}
