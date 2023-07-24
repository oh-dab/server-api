package com.ohdab.member.repository;

import com.ohdab.entity.main.java.com.ohdab.entity.MemberJpa;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.mapper.MemberPersistenceMapper;
import com.ohdab.member.repository.repository.MemberRepository;
import com.ohdab.member.service.port.out.FindMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FindMemberDbAdapter implements FindMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findByName(String name) {
        Optional<MemberJpa> memberJpaOpt = memberRepository.findByName(name);
        if (memberJpaOpt.isEmpty()) {
            return Optional.empty();
        }
        Member member = MemberPersistenceMapper.toDomain(memberJpaOpt.get());
        return Optional.of(member);
    }
}
