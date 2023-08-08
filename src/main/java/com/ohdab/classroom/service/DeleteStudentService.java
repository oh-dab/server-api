package com.ohdab.classroom.service;

import com.ohdab.classroom.service.usecase.DeleteStudentUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteStudentService implements DeleteStudentUsecase {

    @Override
    public void deleteStudent(long classroomId, long studentId) {}
}
