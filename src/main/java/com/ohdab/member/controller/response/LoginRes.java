package com.ohdab.member.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRes {

    private String message;
    private Long memberId;
    private String jwtToken;
}
