package com.ohdab.member.repository.mapper;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
class MemberMapperTest {

    @Autowired private MemberMapper memberMapper;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager em;

    @DisplayName("특정 반의 학생 명단을 조회한다.")
    @Test
    void 교재_상세조회_학생명단() {
        // given
        final Member student1 =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("갑").password("1234").build())
                        .authorities(List.of(new Authority("STUDENT")))
                        .build();
        final Member student2 =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("을").password("1234").build())
                        .authorities(List.of(new Authority("STUDENT")))
                        .build();
        final Member student3 =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("병").password("1234").build())
                        .authorities(List.of(new Authority("STUDENT")))
                        .build();

        Member savedStudent1 = memberRepository.save(student1);
        Member savedStudent2 = memberRepository.save(student2);
        Member savedStudent3 = memberRepository.save(student3);

        em.clear();
        em.flush();

        // when
        List<StudentInfoDto> result =
                memberMapper.findAllStudent(
                        List.of(
                                savedStudent1.getId(),
                                savedStudent2.getId(),
                                savedStudent3.getId()));

        // then
        //        assertThat(result).hasSize(3);
        //                .extracting("studentId", "name")
        //                .contains(tuple(savedStudent1.getId(), "값"))
        //                .contains(tuple(savedStudent2.getId(), "을"))
        //                .contains(tuple(savedStudent3.getId(), "병"));
    }
}
