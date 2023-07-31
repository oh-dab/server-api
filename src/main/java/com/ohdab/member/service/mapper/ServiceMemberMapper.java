package com.ohdab.member.service.mapper;

import com.ohdab.member.domain.Member;
import com.ohdab.member.service.dto.MemberDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceMemberMapper {

    public MemberDto memberDomainToMemberDto(Member member) {
        List<String> authorities = new ArrayList<>();
        member.getAuthorities().forEach(auth -> authorities.add(auth.getRole()));
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getMemberInfo().getName())
                .password(member.getMemberInfo().getPassword())
                .authorities(authorities)
                .build();
    }
}
