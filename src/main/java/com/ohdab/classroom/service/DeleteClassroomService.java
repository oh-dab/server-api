package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.helper.ClassroomServiceHelper;
import com.ohdab.classroom.service.usecase.DeleteClassroomUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeleteClassroomService implements DeleteClassroomUsecase {

    private final ClassroomRepository classroomRepository;

    @Override
    public void deleteClassroomById(long classroomId) {
        Classroom classroom =
                ClassroomServiceHelper.getClassroomById(classroomId, classroomRepository);
        classroomRepository.delete(classroom);
    }
}