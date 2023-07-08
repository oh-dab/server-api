package com.ohdab.helper;

import com.ohdab.domain.Member;
import com.ohdab.exception.NoMemberException;
import com.ohdab.port.out.FindMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class MemberHelperService {

    private final FindMemberPort findMemberPort;

    public Member findExistingMember(String name) {
        return findMemberPort
                .findByMemberProfile_Name(name)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }
}
