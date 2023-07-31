package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.AddTeacherReqDto;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddTeacherService.class, MemberHelperService.class})
public class AddTeacherServiceTest {

    @Autowired private AddTeacherUsecase addTeacherUsecase;
    @MockBean private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님 추가 성공 테스트")
    void 선생님_추가_성공() {
        // given
        String name = "선생님";
        AddTeacherReqDto addTeacherReqDto = AddTeacherReqDto.builder().name(name).build();

        // when
        Mockito.when(memberRepository.existsByMemberInfoName(Mockito.anyString()))
                .thenReturn(false);

        // then
        assertThatNoException().isThrownBy(() -> addTeacherUsecase.addTeacher(addTeacherReqDto));
    }
}
