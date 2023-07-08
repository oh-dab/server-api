package com.ohdab.mapper;

import com.ohdab.domain.Member;
import com.ohdab.domain.authority.Authority;
import com.ohdab.domain.profile.Profile;
import com.ohdab.entity.AuthorityVO;
import com.ohdab.entity.MemberEntity;
import com.ohdab.entity.ProfileVO;
import java.util.List;
import java.util.stream.Collectors;

public class MemberPersistenceMapper {

    public static Member toDomain(MemberEntity memberEntity) {

        return Member.builder()
                .id(memberEntity.getId())
                .profile(toMemberProfile(memberEntity))
                .authorities(toAuthority(memberEntity))
                .build();
    }

    private static Profile toMemberProfile(MemberEntity memberEntity) {
        return Profile.builder()
                .name(memberEntity.getProfileVO().getName())
                .password(memberEntity.getProfileVO().getPassword())
                .build();
    }

    private static List<Authority> toAuthority(MemberEntity memberEntity) {
        return memberEntity.getAuthorities().stream()
                .map(authorityVO -> Authority.builder().role(authorityVO.getRole()).build())
                .collect(Collectors.toList());
    }

    public static MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .profileVO(toMemberProfileJpa(member))
                .authorities(toAuthorityJpa(member))
                .build();
    }

    private static ProfileVO toMemberProfileJpa(Member member) {
        return ProfileVO.builder()
                .name(member.getProfile().getName())
                .password(member.getProfile().getPassword())
                .build();
    }

    private static List<AuthorityVO> toAuthorityJpa(Member member) {
        return member.getAuthorities().stream()
                .map(authority -> AuthorityVO.builder().role(authority.getRole()).build())
                .collect(Collectors.toList());
    }
}
