package com.ohdab.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginReqDto {

    private String name;
    private String password;
}
