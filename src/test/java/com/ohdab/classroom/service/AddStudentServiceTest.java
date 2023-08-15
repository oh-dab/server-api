package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.AddStudentDto;
import com.ohdab.classroom.service.usecase.AddStudentUsecase;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddStudentService.class})
class AddStudentServiceTest {

    @Autowired private AddStudentUsecase addStudentUsecase;
    @Autowired private ApplicationEventPublisher publisher;
    @MockBean private ClassroomRepository classroomRepository;
    @MockBean private MemberRepository memberRepository;
    @MockBean private MistakeNoteRepository mistakeNoteRepository;

    @DisplayName("addStudent 메서드는")
    @Nested
    class AddStudent {

        @DisplayName("해당 반이 존재한다면")
        @Nested
        class ExistClassroom {

            @DisplayName("회원가입 이벤트 발생, 해당 반 학생 목록에 학생 id를 추가 및 해당 반의 교재에 대한 모든 오답노트를 생성한다.")
            @Test
            void joinStudentAndAddStudent() {
                // given
                final AddStudentDto.Request addStudentReq =
                        AddStudentDto.Request.builder().classroomId(1L).studentName("갑").build();

                final Classroom classroom =
                        Classroom.builder()
                                .teacher(new TeacherId(10L))
                                .classroomInfo(
                                        ClassroomInfo.builder()
                                                .name("1반")
                                                .description("설명")
                                                .grade(Grade.HIGH_1)
                                                .build())
                                .build();

                // when
                when(classroomRepository.findById(anyLong()))
                        .thenReturn(Optional.ofNullable(classroom));
                when(memberRepository.findByMemberInfoName(anyString()))
                        .thenReturn(Optional.ofNullable((mock(Member.class))));
                when(mock(Member.class).getId()).thenReturn(20L);
                addStudentUsecase.addStudent(addStudentReq);

                // then
                assertThat(classroom.getStudents()).hasSize(1);
            }
        }

        @DisplayName("해당 반이 존재하지 않는다면")
        @Nested
        class DoesNotExistClassroom {
            @DisplayName("NoClassroomException 예외를 던진다.")
            @Test
            void throwNoClassroomException() {
                // given
                final AddStudentDto.Request addStudentReq =
                        AddStudentDto.Request.builder().classroomId(1L).studentName("갑").build();

                // when
                when(classroomRepository.findById(anyLong())).thenReturn(Optional.empty());

                // then
                assertThatThrownBy(() -> addStudentUsecase.addStudent(addStudentReq))
                        .isInstanceOf(NoClassroomException.class);
            }
        }
    }
}
