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
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddStudentService implements AddStudentUsecase {

    private final ApplicationEventPublisher publisher;
    private final ClassroomRepository classroomRepository;
    private final MemberRepository memberRepository;
    private final MistakeNoteRepository mistakeNoteRepository;

    @Override
    public void addStudent(AddStudentDto.Request addStudentReq) {
        Classroom classroom = findClassroomById(addStudentReq.getClassroomId());
        publishStudentAddedEvent(addStudentReq.getStudentName());
        Member student = findMemberByName(addStudentReq.getStudentName());
        createMistakeNoteForAddedStudent(classroom, student);
        classroom.addStudent(new StudentId(student.getId()));
    }

    private Classroom findClassroomById(long classroomId) {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow(() -> new NoClassroomException("존재하지 않는 반입니다."));
    }

    private void publishStudentAddedEvent(String studentName) {
        publisher.publishEvent(StudentAddedEvent.builder().studentName(studentName).build());
    }

    private Member findMemberByName(String studentName) {
        return memberRepository
                .findByMemberInfoName(studentName)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }

    private void createMistakeNoteForAddedStudent(Classroom classroom, Member student) {
        long studentId = student.getId();
        List<WorkbookId> workbooks = classroom.getWorkbooks();
        List<MistakeNote> mistakeNotes =
                workbooks.stream()
                        .map(
                                workbookId ->
                                        MistakeNote.builder()
                                                .workbookId(workbookId)
                                                .studentId(new StudentId(studentId))
                                                .build())
                        .collect(Collectors.toList());
        mistakeNoteRepository.saveAll(mistakeNotes);
    }
}
