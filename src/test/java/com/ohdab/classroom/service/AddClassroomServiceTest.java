package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomReqDto;
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
@ContextConfiguration(classes = {AddClassroomService.class})
class AddClassroomServiceTest {

    @Autowired private AddClassroomService addClassroomService;
    @MockBean private MemberRepository memberRepository;
    @MockBean private ClassroomRepository classroomRepository;

    @Test
    @DisplayName("반 정보와 선생님 id를 통해 반을 추가 테스트")
    void 반추가() throws Exception {
        // given
        long id = 1L;
        String name = "1반";
        String desc = "1반에 대한 설명입니다.";
        String grade = "high1";
        ClassroomReqDto classroomReqDto =
                ClassroomReqDto.builder()
                        .name(name)
                        .description(desc)
                        .grade(grade)
                        .teacherId(id)
                        .build();
        // when
        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        addClassroomService.addClassroom(classroomReqDto);
        // then
        assertThatNoException().isThrownBy(() -> System.out.println("success"));
    }
}
