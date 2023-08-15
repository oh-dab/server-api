package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.usecase.AddWorkbookUsecase;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddWorkbookService.class})
public class AddWorkbookServiceTest {

    @Autowired private AddWorkbookUsecase addWorkbookUsecase;
    @MockBean private WorkbookRepository workbookRepository;
    @MockBean private ClassroomRepository classroomRepository;
    @MockBean private MistakeNoteRepository mistakeNoteRepository;

    @Test
    @DisplayName("교재 추가 성공 테스트")
    void 교재_추가_성공() {
        // given
        long classroomId = 1L;
        Classroom classroom =
                Classroom.builder()
                        .classroomInfo(
                                ClassroomInfo.builder()
                                        .name("1반")
                                        .description("1반 설명입니다")
                                        .grade(Grade.HIGH_1)
                                        .build())
                        .teacher(new TeacherId(1L))
                        .build();
        long workbookId = 1L;
        ClassroomAddWorkbookDto.Request addWorkbookDto =
                ClassroomAddWorkbookDto.Request.builder()
                        .name("교재")
                        .description("교재 설명입니다.")
                        .startingNumber(1)
                        .endingNumber(2000)
                        .build();
        List<StudentId> studentIdList = new ArrayList<>();
        studentIdList.add(new StudentId(1L));
        studentIdList.add(new StudentId(2L));
        studentIdList.add(new StudentId(3L));

        // when
        when(classroomRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(classroom));
        when(workbookRepository.existsByClassroomIdAndWorkbookInfoName(
                        Mockito.any(), Mockito.any()))
                .thenReturn(false);
        when(workbookRepository.save(Mockito.any())).thenReturn(mock(Workbook.class));
        when(mock(Workbook.class).getId()).thenReturn(workbookId);
        when(classroomRepository.findStudentsById(Mockito.anyLong())).thenReturn(studentIdList);

        // then
        assertThatNoException()
                .isThrownBy(
                        () ->
                                addWorkbookUsecase.addWorkbookByClassroomId(
                                        classroomId, addWorkbookDto));
        assertThat(classroom.getWorkbooks().size()).isEqualTo(workbookId);
        Mockito.verify(mistakeNoteRepository, times(studentIdList.size())).save(Mockito.any());
    }
}
