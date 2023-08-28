package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DeleteTeacherService.class, MemberHelperService.class})
class DeleteTeacherServiceTest {

    @Autowired private DeleteTeacherUsecase deleteTeacherUsecase;
    @Autowired private ApplicationEventPublisher publisher;
    @MockBean private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님 삭제 성공 테스트")
    void 선생님_삭제_성공_테스트() {
        // given
        long teacherId = 1L;

        // when
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mock(Member.class)));
        when(mock(Member.class).getId()).thenReturn(1L);

        // then
        assertThatNoException().isThrownBy(() -> deleteTeacherUsecase.deleteTeacherById(teacherId));
    }
}
