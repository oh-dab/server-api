package com.ohdab.mistakenote.service.helper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MistakeNoteHelperService {

    public Member find(MemberRepository memberRepository, long studentId) {
        return memberRepository.findById(studentId)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }
}
