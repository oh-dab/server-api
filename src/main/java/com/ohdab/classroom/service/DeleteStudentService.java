package com.ohdab.classroom.service;

import static com.ohdab.classroom.service.helper.ClassroomHelperService.findExistingClassroom;
import static com.ohdab.member.service.helper.MemberHelperService.findExistingMemberById;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.usecase.DeleteStudentUsecase;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteStudentService implements DeleteStudentUsecase {

    private final ClassroomRepository classroomRepository;
    private final MemberRepository memberRepository;

    @Override
    public void deleteStudent(long classroomId, long studentId) {
        Classroom classroom = findExistingClassroom(classroomId, classroomRepository);
        Member student = findExistingMemberById(memberRepository, studentId);
        student.withdrawal();
        classroom.deleteStudent(studentId);
    }
}
