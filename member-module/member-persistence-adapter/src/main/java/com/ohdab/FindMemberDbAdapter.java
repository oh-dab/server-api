package com.ohdab;

import com.ohdab.domain.Member;
import com.ohdab.entity.MemberEntity;
import com.ohdab.mapper.MemberPersistenceMapper;
import com.ohdab.port.out.FindMemberPort;
import com.ohdab.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FindMemberDbAdapter implements FindMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findByMemberProfile_Name(String name) {
        Optional<MemberEntity> memberJpaOpt = memberRepository.findByProfileVO_Name(name);
        if (memberJpaOpt.isEmpty()) {
            return Optional.empty();
        }
        Member member = MemberPersistenceMapper.toDomain(memberJpaOpt.get());
        return Optional.of(member);
    }
}
