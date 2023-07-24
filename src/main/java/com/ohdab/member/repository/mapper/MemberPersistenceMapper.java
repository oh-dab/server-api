package com.ohdab.member.repository.mapper;

import com.ohdab.entity.main.java.com.ohdab.entity.AuthorityJpa;
import com.ohdab.entity.main.java.com.ohdab.entity.MemberJpa;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.authority.Authority;
import com.ohdab.member.domain.profile.Profile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPersistenceMapper {

    public static Member toDomain(MemberJpa memberJpa) {
        return Member.builder()
                .id(memberJpa.getId())
                .profile(toMemberProfile(memberJpa))
                .authorities(toAuthority(memberJpa))
                .build();
    }

    private static Profile toMemberProfile(MemberJpa memberEntity) {
        return Profile.builder()
                .name(memberEntity.getName())
                .password(memberEntity.getPassword())
                .build();
    }

    private static List<Authority> toAuthority(MemberJpa memberEntity) {
        return memberEntity.getAuthorities().stream()
                .map(authorityVO -> Authority.builder().role(authorityVO.getRole()).build())
                .collect(Collectors.toList());
    }

    public static MemberJpa toEntity(Member member) {
        return MemberJpa.builder()
                .name(member.getProfile().getName())
                .password(member.getProfile().getPassword())
                .authorities(toAuthorityJpa(member))
                .build();
    }

    private static List<AuthorityJpa> toAuthorityJpa(Member member) {
        return member.getAuthorities().stream()
                .map(authority -> AuthorityJpa.builder().role(authority.getRole()).build())
                .collect(Collectors.toList());
    }
}
