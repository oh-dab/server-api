package com.ohdab.member.controller.mapper;

import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.JoinRes;
import com.ohdab.member.controller.response.LoginRes;
import com.ohdab.member.service.dto.JoinReqDto;
import com.ohdab.member.service.dto.LoginReqDto;
import com.ohdab.member.service.dto.LoginResDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static LoginRes toLoginRes(LoginResDto loginResDto) {
        return LoginRes.builder()
                .message("로그인에 성공하였습니다.")
                .memberId(loginResDto.getMemberId())
                .jwtToken(loginResDto.getJwtToken())
                .build();
    }

    public static JoinRes toJoinRes() {
        return JoinRes.builder().message("회원가입이 완료되었습니다.").build();
    }
}
