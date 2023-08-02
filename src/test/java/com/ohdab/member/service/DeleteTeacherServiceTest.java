package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DeleteTeacherService.class})
public class DeleteTeacherServiceTest {

    @Autowired private DeleteTeacherUsecase deleteTeacherUsecase;
    @MockBean private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님 삭제 성공 테스트")
    void 선생님_삭제_성공_테스트() {
        // given
        long id = 1;

        // when
        Mockito.when(memberRepository.existsById(id)).thenReturn(true);

        // then
        assertThatNoException().isThrownBy(() -> deleteTeacherUsecase.deleteTeacherById(id));
    }
}
