package com.ohdab.classroom.service.usecase;

import com.ohdab.classroom.service.dto.AddStudentDto;

public interface AddStudentUsecase {

    void addStudent(AddStudentDto.Request addStudentReq);
}
