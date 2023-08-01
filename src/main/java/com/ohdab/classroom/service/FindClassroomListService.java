package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.mapper.ClassroomServiceMapper;
import com.ohdab.classroom.service.usecase.FindClassroomListUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindClassroomListService implements FindClassroomListUsecase {

    private final ClassroomRepository classroomRepository;

    @Override
    public List<ClassroomDto.Response> findClassroomListByTeacherId(long teacherId) {
        List<Classroom> classroomList = classroomRepository.findAllByTeacherId(teacherId);
        return ClassroomServiceMapper.classroomListToClassroomDtoList(classroomList);
    }
}
