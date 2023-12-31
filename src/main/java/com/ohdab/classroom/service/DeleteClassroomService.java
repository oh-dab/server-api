package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.helper.ClassroomHelperService.findExistingClassroom;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.usecase.DeleteClassroomUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteClassroomService implements DeleteClassroomUsecase {

    private final ClassroomRepository classroomRepository;

    @Override
    public void deleteClassroomById(long classroomId) {
        Classroom classroom = findExistingClassroom(classroomId, classroomRepository);
        classroomRepository.delete(classroom);
    }
}
