package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.event.StudentAddedEvent;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.AddStudentDto;
import com.ohdab.classroom.service.usecase.AddStudentUsecase;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddStudentService implements AddStudentUsecase {

    private final ApplicationEventPublisher publisher;
    private final ClassroomRepository classroomRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addStudent(AddStudentDto.Request addStudentReq) {
        Classroom classroom =
                classroomRepository
                        .findById(addStudentReq.getClassroomId())
                        .orElseThrow(() -> new NoClassroomException("존재하지 않는 반입니다."));
        publisher.publishEvent(
                StudentAddedEvent.builder().studentName(addStudentReq.getStudentName()).build());
        Member member =
                memberRepository
                        .findByMemberInfoName(addStudentReq.getStudentName())
                        .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
        classroom.addStudent(new StudentId(member.getId()));
    }
}
