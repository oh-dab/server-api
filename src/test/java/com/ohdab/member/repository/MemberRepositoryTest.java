package com.ohdab.member.repository;

import static com.ohdab.member.domain.MemberStatus.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @DisplayName("선생님 목록 조회 성공 테스트")
    void 선생님_목록_조회_성공() {
        // given
        Authority teacher = new Authority("TEACHER");
        Authority student = new Authority("STUDENT");
        Member teacher1 = memberRepository.save(createMember("선생님", "tjstodsla", teacher));
        Member teacher2 = memberRepository.save(createMember("선생님2", "tjstodsla2", teacher));
        Member teacher3 = memberRepository.save(createMember("선생님3", "tjstodsla3", teacher));
        Member student1 = memberRepository.save(createMember("학생", "gkrtod", student));
        entityManager.flush();
        entityManager.clear();

        // when
        List<Member> results = memberRepository.findByAuthoritiesContaining(teacher);

        // then
        assertThat(results)
                .hasSize(3)
                .extracting(
                        Member::getId,
                        m -> m.getMemberInfo().getName(),
                        m -> m.getMemberInfo().getPassword(),
                        m -> m.getAuthorities().get(0).getRole(),
                        Member::getStatus)
                .contains(
                        tuple(
                                teacher1.getId(),
                                teacher1.getMemberInfo().getName(),
                                teacher1.getMemberInfo().getPassword(),
                                teacher1.getAuthorities().get(0).getRole(),
                                teacher1.getStatus()),
                        tuple(
                                teacher2.getId(),
                                teacher2.getMemberInfo().getName(),
                                teacher2.getMemberInfo().getPassword(),
                                teacher2.getAuthorities().get(0).getRole(),
                                teacher2.getStatus()),
                        tuple(
                                teacher3.getId(),
                                teacher3.getMemberInfo().getName(),
                                teacher3.getMemberInfo().getPassword(),
                                teacher3.getAuthorities().get(0).getRole(),
                                teacher3.getStatus()));
    }

    @Test
    @DisplayName("선생님 추가 성공 테스트")
    void 선생님_추가_성공() {
        // given
        Authority teacher = new Authority("TEACHER");
        Member member = createMember("선생님", "tjstodsla", teacher);

        // when
        memberRepository.save(member);
        Member result = memberRepository.findById(member.getId()).get();

        // then
        assertThat(result)
                .extracting(
                        Member::getId,
                        m -> m.getMemberInfo().getName(),
                        m -> m.getMemberInfo().getPassword(),
                        m -> m.getAuthorities().get(0).getRole(),
                        Member::getStatus)
                .containsExactly(
                        member.getId(),
                        member.getMemberInfo().getName(),
                        member.getMemberInfo().getPassword(),
                        member.getAuthorities().get(0).getRole(),
                        member.getStatus());
    }

    @Test
    @DisplayName("선생님 삭제(탈퇴) 성공 테스트")
    void 선생님_삭제_및_탈퇴_성공() {
        // given
        Authority teacher = new Authority("TEACHER");
        Member member = createMember("선생님", "tjstodsla", teacher);

        // when
        memberRepository.save(member);
        member.withdrawal();
        Member result = memberRepository.findById(member.getId()).get();

        // then
        assertThat(result)
                .extracting(Member::getId, Member::getStatus)
                .containsExactly(member.getId(), INACTIVE);
    }

    private Member createMember(String name, String password, Authority role) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(role);
        return Member.builder()
                .memberInfo(MemberInfo.builder().name(name).password(password).build())
                .authorities(authorities)
                .build();
    }
}
