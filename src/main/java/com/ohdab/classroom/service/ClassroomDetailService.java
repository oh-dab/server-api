package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.dto.ClassroomDetailDto.ClassroomDetailDtoResponse;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.mapper.ClassroomDetailServiceMapper;
import com.ohdab.classroom.service.usecase.ClassroomDetailUsecase;
import com.ohdab.core.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassroomDetailService implements ClassroomDetailUsecase {

    private final ClassroomRepository classroomRepository;

    @Override
    public ClassroomDetailDtoResponse getClassroomDetailById(long classroomId) {
        Classroom classroom =
                classroomRepository
                        .findById(classroomId)
                        .orElseThrow(
                                () ->
                                        new NoClassroomException(
                                                ExceptionEnum.NO_CLASSROOM.getMessage()));
        return ClassroomDetailServiceMapper.classroomToClassroomDetail(classroom);
    }
}
