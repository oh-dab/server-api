package com.ohdab.member.domain;

import com.ohdab.member.domain.authority.Authority;
import com.ohdab.member.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Getter
public class Member {
    Long id;
    Profile profile;
    List<Authority> authorities;

    @Builder
    public Member(Long id, Profile profile, List<Authority> authorities) {
        this.id = id;
        this.profile = profile;
        this.authorities = authorities;
    }

    public boolean matchPassword(
            PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
