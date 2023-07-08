package com.ohdab.domain;

import com.ohdab.domain.authority.Authority;
import com.ohdab.domain.profile.Profile;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

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
