package com.ohdab.mistakenote.service.helper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MistakeNoteHelperService {

    public boolean isNotExistingMember(MemberRepository memberRepository, long memberId) {
        Optional<Member> memberOpt = memberRepository.findActiveMemberById(memberId);
        return memberOpt.isEmpty();
    }
}
