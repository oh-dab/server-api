package com.ohdab.member.service.mapper;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.service.dto.MemberDto;
import java.util.ArrayList;
import java.util.List;

public class ServiceMemberMapper {

    public static List<MemberDto> memberDomainListToMemberDtoList(List<Member> members) {
        List<MemberDto> memberDtoList = new ArrayList<>();
        members.forEach(m -> memberDtoList.add(memberDomainToMemberDto(m)));
        return memberDtoList;
    }

    private static MemberDto memberDomainToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getMemberInfo().getName())
                .password(member.getMemberInfo().getPassword())
                .authorities(createAuthoritiesDto(member.getAuthorities()))
                .build();
    }

    private static List<String> createAuthoritiesDto(List<Authority> authorities) {
        List<String> authoritiesDto = new ArrayList<>();
        authorities.forEach(auth -> authoritiesDto.add(auth.getRole()));
        return authoritiesDto;
    }
}
