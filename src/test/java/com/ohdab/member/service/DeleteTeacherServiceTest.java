package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
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
@ContextConfiguration(classes = {DeleteTeacherService.class, MemberHelperService.class})
public class DeleteTeacherServiceTest {

    @Autowired private DeleteTeacherUsecase deleteTeacherUsecase;
    @MockBean private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님 삭제 성공 테스트")
    void 선생님_삭제_성공_테스트() {
        // given
        long id = 1;
        Member member = createTeacher("선생님", "tjstodsla");

        // when
        Mockito.when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(member));

        // then
        assertThatNoException().isThrownBy(() -> deleteTeacherUsecase.deleteTeacherById(id));
        assertThat(member.getStatus().name()).isEqualTo("INACTIVE");
    }

    private Member createTeacher(String name, String password) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("TEACHER"));
        return Member.builder()
                .memberInfo(MemberInfo.builder().name(name).password(password).build())
                .authorities(authorities)
                .build();
    }
}
