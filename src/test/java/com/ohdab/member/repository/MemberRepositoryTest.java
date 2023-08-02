package com.ohdab.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
public class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager entityManager;

    @Test
    @DisplayName("선생님 목록 조회 성공 테스트")
    void 선생님_목록_조회_성공() {
        // given
        Authority teacher = new Authority("TEACHER");
        Authority student = new Authority("STUDENT");
        memberRepository.save(createMember("선생님", "tjstodsla", teacher));
        memberRepository.save(createMember("선생님2", "tjstodsla2", teacher));
        memberRepository.save(createMember("선생님3", "tjstodsla3", teacher));
        memberRepository.save(createMember("학생", "gkrtod", student));
        entityManager.flush();
        entityManager.clear();

        // when
        List<Member> results = memberRepository.findByAuthoritiesContaining(teacher);

        // then
        assertThat(results.size()).isEqualTo(3);
        assertThat(results.get(0).getMemberInfo().getName()).isEqualTo("선생님");
        assertThat(results.get(0).getMemberInfo().getPassword()).isEqualTo("tjstodsla");
        assertThat(results.get(0).getAuthorities().get(0).getRole()).isEqualTo("TEACHER");
        assertThat(results.get(1).getMemberInfo().getName()).isEqualTo("선생님2");
        assertThat(results.get(1).getMemberInfo().getPassword()).isEqualTo("tjstodsla2");
        assertThat(results.get(1).getAuthorities().get(0).getRole()).isEqualTo("TEACHER");
        assertThat(results.get(2).getMemberInfo().getName()).isEqualTo("선생님3");
        assertThat(results.get(2).getMemberInfo().getPassword()).isEqualTo("tjstodsla3");
        assertThat(results.get(2).getAuthorities().get(0).getRole()).isEqualTo("TEACHER");
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
