package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.exception.CannotFindGradeException;
import com.ohdab.classroom.exception.CannotFindTeacherException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.usecase.AddClassroomUsecase;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddAddClassroomService implements AddClassroomUsecase {

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

    private void throwIfTeacherDoesNotExist(Long teacherId) {
        if (!memberRepository.existsById(teacherId)) {
            throw new CannotFindTeacherException("cannot find teacher by id : " + teacherId);
        }
    }

    private Grade findGradeByString(String stringGrade) {
        try {
            return Grade.valueOfLabel(stringGrade);
        } catch (Exception e) {
            throw new CannotFindGradeException("Cannot find Grade : " + stringGrade, e);
        }
    }
}
