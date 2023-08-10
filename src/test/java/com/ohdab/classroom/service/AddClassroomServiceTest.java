package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomDto;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddClassroomService.class, ClassroomHelperService.class})
class AddClassroomServiceTest {

    @Autowired private AddClassroomService addClassroomService;
    @Autowired private ClassroomHelperService classroomHelperService;
    @MockBean private MemberRepository memberRepository;
    @MockBean private ClassroomRepository classroomRepository;

    @Test
    @DisplayName("반 정보와 선생님 id를 통해 반을 추가 테스트")
    void 반_추가() throws Exception {
        // given
        long id = 1L;
        String name = "1반";
        String desc = "1반에 대한 설명입니다.";
        String grade = "high1";
        ClassroomDto.Request classroomReqDto =
                ClassroomDto.Request.builder()
                        .info(
                                ClassroomDto.Info.builder()
                                        .name(name)
                                        .description(desc)
                                        .grade(grade)
                                        .build())
                        .teacherId(id)
                        .build();
        // when
        when(memberRepository.existsById(anyLong())).thenReturn(true);

        // then
        assertThatNoException().isThrownBy(() -> addClassroomService.addClassroom(classroomReqDto));
    }
}
