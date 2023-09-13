package com.ohdab.member.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    private String role;

    @Builder
    public Authority(String role) {
        this.role = role;
    }
}
