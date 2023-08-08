package com.ohdab.member.service.helper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class MemberHelperService {

    public Member findExistingMemberByName(MemberRepository memberRepository, String name) {
        return memberRepository
                .findByMemberInfoName(name)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }

    public Member findExistingMemberById(MemberRepository memberRepository, long id) {
        return memberRepository
                .findById(id)
                .orElseThrow(() -> new NoMemberException("Unknown member with id \"" + id + "\""));
    }

    public boolean checkIfMemberExistByName(MemberRepository memberRepository, String name) {
        return memberRepository.existsByMemberInfoName(name);
    }
}
