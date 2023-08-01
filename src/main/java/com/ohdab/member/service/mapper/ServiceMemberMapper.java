package com.ohdab.member.service.mapper;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.service.dto.MemberDto;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceMemberMapper {

    public static List<MemberDto> memberDomainListToMemberDtoList(List<Member> members) {
        return members.stream()
                .map(
                        member ->
                                MemberDto.builder()
                                        .id(member.getId())
                                        .name(member.getMemberInfo().getName())
                                        .password(member.getMemberInfo().getPassword())
                                        .authorities(createAuthoritiesDto(member.getAuthorities()))
                                        .build())
                .collect(Collectors.toList());
    }

    private static List<String> createAuthoritiesDto(List<Authority> authorities) {
        return authorities.stream().map(auth -> auth.getRole()).collect(Collectors.toList());
    }
}
