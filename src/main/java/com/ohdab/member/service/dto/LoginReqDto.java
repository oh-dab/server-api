package com.ohdab.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginReqDto {

    private String name;
    private String password;
}
