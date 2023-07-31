package com.ohdab.member.service.mapper;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.service.dto.MemberDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceMemberMapper {

    public Member memberDtoToMemberDomain(MemberDto memberDto) {
        List<Authority> authorities = new ArrayList<>();
        memberDto.getAuthorities().forEach(auth -> authorities.add(new Authority(auth)));
        return Member.builder()
                .memberInfo(
                        MemberInfo.builder()
                                .name(memberDto.getName())
                                .password(memberDto.getPassword())
                                .build())
                .authorities(authorities)
                .build();
    }
}
