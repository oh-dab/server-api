package com.ohdab.domain.authority;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Authority {

    private String role;

    @Builder
    public Authority(String role) {
        this.role = role;
    }
}
