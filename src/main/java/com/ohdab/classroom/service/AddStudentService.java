package com.ohdab.classroom.service;

import com.ohdab.classroom.service.dto.AddStudentDto;
import com.ohdab.classroom.service.usecase.AddStudentUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddStudentService implements AddStudentUsecase {

    @Override
    public void addStudent(AddStudentDto.Request addStudentReq) {}
}
