package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.classroom.service.usecase.DeleteStudentUsecase;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.helper.MemberHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteStudentService implements DeleteStudentUsecase {

    private final ClassroomHelperService classroomHelperService;
    private final MemberHelperService memberHelperService;
    private final ClassroomRepository classroomRepository;
    private final MemberRepository memberRepository;

    @Override
    public void deleteStudent(long classroomId, long studentId) {
        Classroom classroom =
                classroomHelperService.getClassroomById(classroomId, classroomRepository);
        Member student = memberHelperService.findExistingMemberById(memberRepository, studentId);
        student.withdrawal();
        classroom.deleteStudent(studentId);
    }
}
