package com.ohdab.member.domain;

import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded private MemberProfile memberProfile;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "AUTHORITY_LIST", joinColumns = @JoinColumn(name = "member_id"))
    private List<Authority> authorityList;

    @Builder
    public Member(MemberProfile memberProfile, List<Authority> authorityList) {
        this.memberProfile = memberProfile;
        this.authorityList = authorityList;
    }

    public boolean matchPassword(
            PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
