package com.ohdab.mapper;

import com.ohdab.dto.JoinReqDto;
import com.ohdab.dto.LoginReqDto;
import com.ohdab.dto.LoginResDto;
import com.ohdab.request.JoinReq;
import com.ohdab.request.LoginReq;
import com.ohdab.response.LoginRes;

public class MemberWebMapper {

    public static JoinReqDto toJoinReqDto(JoinReq joinReq) {
        return JoinReqDto.builder()
                .name(joinReq.getName())
                .password(joinReq.getPassword())
                .role(joinReq.getRole())
                .build();
    }

    public static LoginReqDto toLoginReqDto(LoginReq loginReq) {
        return LoginReqDto.builder()
                .name(loginReq.getName())
                .password(loginReq.getPassword())
                .build();
    }

    public static LoginRes toLoginResDto(LoginResDto loginResDto) {
        return LoginRes.builder()
                .message("로그인에 성공하였습니다.")
                .memberId(loginResDto.getMemberId())
                .jwtToken(loginResDto.getJwtToken())
                .build();
    }
}
