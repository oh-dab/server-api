package com.ohdab.member.service.helper;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.member.domain.Member;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class MemberHelperService {

    public static Member findExistingMemberByName(MemberRepository memberRepository, String name) {
        return memberRepository
                .findByMemberInfoName(name)
                .orElseThrow(() -> new NoMemberException(ExceptionEnum.NO_MEMBER.getMessage()));
    }

    public static Member findExistingMemberById(MemberRepository memberRepository, long id) {
        return memberRepository
                .findById(id)
                .orElseThrow(() -> new NoMemberException(ExceptionEnum.NO_MEMBER.getMessage()));
    }

    public static boolean checkIfMemberExistByName(MemberRepository memberRepository, String name) {
        return memberRepository.existsByMemberInfoName(name);
    }
}
