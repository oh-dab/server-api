package com.ohdab.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetTeacherListService.class})
class GetTeacherListServiceTest {

    @Autowired private GetTeacherListUsecase getTeacherListUsecase;
    @MockBean private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님 목록 조회 성공 테스트")
    void 선생님_목록_조회_성공() {
        // given
        List<Member> teachers = new ArrayList<>();
        teachers.add(createTeacher("선생님1", "tjstodsla1"));
        teachers.add(createTeacher("선생님2", "tjstodsla2"));
        teachers.add(createTeacher("선생님3", "tjstodsla3"));

        // when
        Mockito.when(memberRepository.findByAuthoritiesContaining(Mockito.any()))
                .thenReturn(teachers);
        List<MemberDtoForGetTeacherList.Response> results = getTeacherListUsecase.getTeacherList();

        // then
        assertThat(results)
                .hasSize(3)
                .extracting(m -> m.getName(), m -> m.getAuthorities().get(0), m -> m.getStatus())
                .contains(
                        tuple("선생님1", "TEACHER", "ACTIVE"),
                        tuple("선생님2", "TEACHER", "ACTIVE"),
                        tuple("선생님3", "TEACHER", "ACTIVE"));
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
