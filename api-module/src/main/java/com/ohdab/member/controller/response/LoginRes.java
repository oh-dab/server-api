package com.ohdab.member.controller.response;

import com.ohdab.member.application.service.dto.LoginResDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRes {

    private String message;
    private Long memberId;
    private String jwtToken;

    public static LoginRes toRes(LoginResDto loginResDto) {
        return LoginRes.builder()
                .message("로그인에 성공하였습니다.")
                .memberId(loginResDto.getMemberId())
                .jwtToken(loginResDto.getJwtToken())
                .build();
    }
}
