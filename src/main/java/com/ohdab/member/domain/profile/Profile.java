package com.ohdab.member.domain.profile;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Profile {

    private String name;
    private String password;

    @Builder
    public Profile(String name, String password) {
        this.name = name;
        this.password = password;
    }
}