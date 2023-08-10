package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.exception.NoStudentException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.DeleteStudentDto;
import com.ohdab.classroom.service.usecase.DeleteStudentUsecase;
import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.MemberStatus;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.member.exception.AlreadyWithdrawlException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.helper.MemberHelperService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DeleteStudentService.class, MemberHelperService.class})
class DeleteStudentServiceTest {

    @Autowired private DeleteStudentUsecase deleteStudentUsecase;
    @Autowired private MemberHelperService memberHelperService;
    @MockBean private ClassroomRepository classroomRepository;
    @MockBean private MemberRepository memberRepository;

    @DisplayName("deleteStudent 메서드는")
    @Nested
    class deleteStudent {

        @DisplayName("요청받은 교실이 존재하고")
        @Nested
        class ifClassroomExist {

            @DisplayName("삭제하려는 학생이 정상 회원이라면")
            @Nested
            class ifActiveStudent {

                @DisplayName("해당 학생을 학생 목록에서 삭제하고 탈퇴처리한다.")
                @Test
                void removeAndExpulsion() {
                    // given
                    final long classroomId = 1L;
                    final long studentId = 2L;
                    final DeleteStudentDto.Request requestDto =
                            DeleteStudentDto.Request.builder()
                                    .classroomId(classroomId)
                                    .studentId(studentId)
                                    .build();

                    final Classroom classroom =
                            Classroom.builder()
                                    .classroomInfo(
                                            ClassroomInfo.builder()
                                                    .name("1반")
                                                    .description("설명")
                                                    .grade(Grade.HIGH_1)
                                                    .build())
                                    .teacher(new TeacherId(10L))
                                    .build();
                    classroom.addStudent(new StudentId(studentId));

                    final Member student =
                            Member.builder()
                                    .memberInfo(
                                            MemberInfo.builder().name("값").password("1234").build())
                                    .authorities(List.of(new Authority("STUDENT")))
                                    .build();

                    // when
                    when(classroomRepository.findClassroomById(anyLong())).thenReturn(classroom);
                    when(memberRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(student));

                    // then
                    assertThatCode(() -> deleteStudentUsecase.deleteStudent(classroomId, studentId))
                            .doesNotThrowAnyException();
                    assertThat(student.getStatus()).isEqualTo(MemberStatus.INACTIVE);
                    assertThat(classroom.getStudents()).isEmpty();
                }
            }

            @DisplayName("삭제하려는 학생이 교실에 없는 학생이라면")
            @Nested
            class ifNotInClassroom {

                @DisplayName("NoStudentException 예외를 던진다.")
                @Test
                void throwNoStudentException() {
                    // given
                    final long classroomId = 1L;
                    final long studentId = 2L;
                    final DeleteStudentDto.Request requestDto =
                            DeleteStudentDto.Request.builder()
                                    .classroomId(classroomId)
                                    .studentId(studentId)
                                    .build();

                    final Classroom classroom =
                            Classroom.builder()
                                    .classroomInfo(
                                            ClassroomInfo.builder()
                                                    .name("1반")
                                                    .description("설명")
                                                    .grade(Grade.HIGH_1)
                                                    .build())
                                    .teacher(new TeacherId(10L))
                                    .build();
                    classroom.addStudent(new StudentId(3L));

                    final Member student =
                            Member.builder()
                                    .memberInfo(
                                            MemberInfo.builder().name("값").password("1234").build())
                                    .authorities(List.of(new Authority("STUDENT")))
                                    .build();

                    // when
                    when(classroomRepository.findClassroomById(anyLong())).thenReturn(classroom);
                    when(memberRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(student));
                    Throwable thrown =
                            catchException(
                                    () ->
                                            deleteStudentUsecase.deleteStudent(
                                                    classroomId, studentId));

                    // then
                    assertThat(thrown).isInstanceOf(NoStudentException.class);
                }
            }

            @DisplayName("삭제하려는 학생이 이미 탈퇴하였다면")
            @Nested
            class ifInActiveStudent {

                @DisplayName("AlreadyWithdrawlException 예외를 던진다.")
                @Test
                void throwAlreadyWithdrawlException() {
                    // given
                    final long classroomId = 1L;
                    final long studentId = 2L;
                    final DeleteStudentDto.Request requestDto =
                            DeleteStudentDto.Request.builder()
                                    .classroomId(classroomId)
                                    .studentId(studentId)
                                    .build();

                    final Classroom classroom =
                            Classroom.builder()
                                    .classroomInfo(
                                            ClassroomInfo.builder()
                                                    .name("1반")
                                                    .description("설명")
                                                    .grade(Grade.HIGH_1)
                                                    .build())
                                    .teacher(new TeacherId(10L))
                                    .build();
                    classroom.addStudent(new StudentId(studentId));

                    final Member student =
                            Member.builder()
                                    .memberInfo(
                                            MemberInfo.builder().name("값").password("1234").build())
                                    .authorities(List.of(new Authority("STUDENT")))
                                    .build();
                    student.withdrawal();

                    // when
                    when(classroomRepository.findClassroomById(anyLong())).thenReturn(classroom);
                    when(memberRepository.findById(anyLong()))
                            .thenReturn(Optional.ofNullable(student));
                    Throwable thrown =
                            catchException(
                                    () ->
                                            deleteStudentUsecase.deleteStudent(
                                                    classroomId, studentId));

                    // then
                    assertThat(thrown).isInstanceOf(AlreadyWithdrawlException.class);
                }
            }
        }

        @DisplayName("요청받은 교실이 존재하지 않는다면")
        @Nested
        class ifClassroomDoesNotExist {

            @DisplayName("CannotFindClassroomException 예외를 던진다.")
            @Test
            void throwCannotFindClassroomException() {
                // given
                final long classroomId = 1L;
                final long studentId = 2L;
                final DeleteStudentDto.Request requestDto =
                        DeleteStudentDto.Request.builder()
                                .classroomId(classroomId)
                                .studentId(studentId)
                                .build();

                // when
                when(classroomRepository.findClassroomById(anyLong()))
                        .thenThrow(NoClassroomException.class);
                Throwable thrown =
                        catchException(
                                () -> deleteStudentUsecase.deleteStudent(classroomId, studentId));

                // then
                assertThat(thrown).isInstanceOf(NoClassroomException.class);
            }
        }
    }
}
