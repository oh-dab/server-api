package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;
import static com.ohdab.classroom.service.helper.ClassroomHelperService.findExistingClassroom;
import static com.ohdab.classroom.service.helper.ClassroomHelperService.findGradeByString;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.usecase.UpdateClassroomInfoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateClassroomInfoService implements UpdateClassroomInfoUsecase {

    private final ClassroomRepository classroomRepository;

    @Override
    public void updateClassroomInfo(ClassroomUpdateDtoRequest request) {
        Classroom classroom = findExistingClassroom(request.getClassroomId(), classroomRepository);
        classroom.updateClassroomInfo(
                ClassroomInfo.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .grade(findGradeByString(request.getGrade()))
                        .build());
    }
}
