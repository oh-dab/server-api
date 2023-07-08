package com.ohdab;

import com.ohdab.domain.Member;
import com.ohdab.entity.MemberEntity;
import com.ohdab.mapper.MemberPersistenceMapper;
import com.ohdab.port.out.SaveMemberPort;
import com.ohdab.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SaveMemberAdapter implements SaveMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public void save(Member member) {
        MemberEntity memberEntity = MemberPersistenceMapper.toEntity(member);
        memberRepository.save(memberEntity);
    }
}
