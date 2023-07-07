package com.ohdab.domain;

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
