package com.ohdab.member.application.service.helper;

import com.ohdab.member.application.exception.NoMemberException;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class MemberHelperService {

    private final UserRepository userRepository;

    public Member findExistingMember(String name) {
        return userRepository
                .findByMemberProfile_Name(name)
                .orElseThrow(() -> new NoMemberException("존재하지 않는 회원입니다."));
    }
}
