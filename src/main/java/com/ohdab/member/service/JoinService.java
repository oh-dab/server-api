package com.ohdab.member.service;

import com.ohdab.core.exception.ExceptionEnum;
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
    public void join(MemberDtoForJoin.Request joinReqDto) {
        checkDuplicatedMember(joinReqDto.getName());
        Member member = createMember(joinReqDto, createAuthorities(joinReqDto));
        memberRepository.save(member);
    }

    private void checkDuplicatedMember(String name) {
        Optional<Member> member = memberRepository.findByMemberInfoName(name);
        if (member.isPresent()) {
            throw new DuplicatedMemberException(ExceptionEnum.DUPLICATED_WORKBOOK.getMessage());
        }
    }

    private List<Authority> createAuthorities(MemberDtoForJoin.Request joinReqDto) {
        List<String> roleList = joinReqDto.getRole();
        return roleList.stream()
                .map(role -> Authority.builder().role(role).build())
                .collect(Collectors.toList());
    }

    private Member createMember(MemberDtoForJoin.Request joinReqDto, List<Authority> authorities) {
        return Member.builder()
                .memberInfo(createMemberInfo(joinReqDto))
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
