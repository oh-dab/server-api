package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.dto.ClassroomUpdateDto.ClassroomUpdateDtoRequest;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.classroom.service.usecase.UpdateClassroomInfoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UpdateClassroomInfoService implements UpdateClassroomInfoUsecase {

    private final ClassroomHelperService classroomHelperService;
    private final ClassroomRepository classroomRepository;

    @Override
    public void updateClassroomInfo(ClassroomUpdateDtoRequest request) {
        Classroom classroom =
                classroomHelperService.getClassroomById(
                        request.getClassroomId(), classroomRepository);
        classroom.updateClassroomInfo(
                ClassroomInfo.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .grade(classroomHelperService.findGradeByString(request.getGrade()))
                        .build());
        classroomRepository.save(classroom);
    }
}
