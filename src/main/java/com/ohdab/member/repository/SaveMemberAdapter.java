package com.ohdab.member.repository;

import com.ohdab.entity.main.java.com.ohdab.entity.MemberJpa;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.mapper.MemberPersistenceMapper;
import com.ohdab.member.repository.repository.MemberRepository;
import com.ohdab.member.service.port.out.SaveMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaveMemberAdapter implements SaveMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public void save(Member member) {
        MemberJpa memberJpa = MemberPersistenceMapper.toEntity(member);
        memberRepository.save(memberJpa);
    }
}
