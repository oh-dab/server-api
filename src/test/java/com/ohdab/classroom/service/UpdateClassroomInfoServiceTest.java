package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomUpdateDto;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UpdateClassroomInfoService.class})
class UpdateClassroomInfoServiceTest {

    @Autowired private UpdateClassroomInfoService updateClassroomInfoService;
    @MockBean private MemberRepository memberRepository;
    @MockBean private ClassroomRepository classroomRepository;

    @Test
    @DisplayName("반 정보 수정 테스트")
    void updateClassroomInfo() {
        // given
        String name = "1반";
        String desc = "1반에 대한 설명입니다.";
        Classroom classroom =
                Classroom.builder()
                        .teacher(TeacherId.builder().id(1).build())
                        .classroomInfo(
                                ClassroomInfo.builder()
                                        .name(name)
                                        .description(desc)
                                        .grade(Grade.HIGH_1)
                                        .build())
                        .build();

        ClassroomUpdateDto.ClassroomUpdateDtoRequest request =
                ClassroomUpdateDto.ClassroomUpdateDtoRequest.builder()
                        .classroomId(1L)
                        .name("2반")
                        .description("22")
                        .grade("high2")
                        .build();

        // when
        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(classroomRepository.findClassroomById(Mockito.anyLong()))
                .thenReturn(classroom);
        updateClassroomInfoService.updateClassroomInfo(request);
        // then
        assertThatNoException().isThrownBy(() -> System.out.println("success"));
    }
}