package com.ohdab.member.service.helper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.service.exception.NoMemberException;
import com.ohdab.member.service.port.out.FindMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class MemberHelperService {

    private final FindMemberPort findMemberPort;

    public Member findExistingMember(String name) {
        return findMemberPort
                .findByName(name)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }
}
