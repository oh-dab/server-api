package com.ohdab.member.service;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.exception.DuplicatedMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.usecase.JoinUsecase;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinService implements JoinUsecase {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void join(MemberDtoForJoin.Request memberDtoForJoin) {
        checkDuplicatedMember(memberDtoForJoin.getName());
        Member member = createMember(memberDtoForJoin, createAuthorities(memberDtoForJoin));
        memberRepository.save(member);
    }

    private void checkDuplicatedMember(String name) {
        Optional<Member> member = memberRepository.findByMemberInfoName(name);
        if (member.isPresent()) {
            throw new DuplicatedMemberException("이미 존재하는 회원입니다.");
        }
    }

    private List<Authority> createAuthorities(MemberDtoForJoin.Request memberDtoForJoin) {
        List<String> roleList = memberDtoForJoin.getRole();
        return roleList.stream()
                .map(role -> Authority.builder().role(role).build())
                .collect(Collectors.toList());
    }

    private Member createMember(
            MemberDtoForJoin.Request memberDtoForJoin, List<Authority> authorities) {
        return Member.builder()
                .memberInfo(createMemberInfo(memberDtoForJoin))
                .authorities(authorities)
                .build();
    }

    private MemberInfo createMemberInfo(MemberDtoForJoin.Request memberDtoForJoin) {
        return MemberInfo.builder()
                .name(memberDtoForJoin.getName())
                .password(passwordEncoder.encode(memberDtoForJoin.getPassword()))
                .build();
    }
}
