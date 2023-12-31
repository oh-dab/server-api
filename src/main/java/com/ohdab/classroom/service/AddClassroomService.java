package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.helper.ClassroomHelperService.findGradeByString;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.exception.NoTeacherException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.usecase.AddClassroomUsecase;
import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddClassroomService implements AddClassroomUsecase {

    private final MemberRepository memberRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public void addClassroom(ClassroomDto.Request classroomReqDto) {
        long teacherId = classroomReqDto.getTeacherId();
        throwIfTeacherDoesNotExist(teacherId);

        ClassroomInfo classroomInfo =
                ClassroomInfo.builder()
                        .name(classroomReqDto.getInfo().getName())
                        .description(classroomReqDto.getInfo().getDescription())
                        .grade(findGradeByString(classroomReqDto.getInfo().getGrade()))
                        .build();

        Classroom classroom =
                Classroom.builder()
                        .teacher(TeacherId.builder().id(teacherId).build())
                        .classroomInfo(classroomInfo)
                        .build();

        classroomRepository.save(classroom);
    }

    private void throwIfTeacherDoesNotExist(long teacherId) {
        if (!memberRepository.existsById(teacherId)) {
            throw new NoTeacherException(ExceptionEnum.NO_TEACHER.getMessage());
        }
    }
}
