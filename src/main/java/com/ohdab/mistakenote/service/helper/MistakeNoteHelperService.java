package com.ohdab.mistakenote.service.helper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class MistakeNoteHelperService {

    public static boolean isNotExistingMember(MemberRepository memberRepository, long memberId) {
        Optional<Member> memberOpt = memberRepository.findActiveMemberById(memberId);
        return memberOpt.isEmpty();
    }
}
