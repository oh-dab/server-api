package com.ohdab.member.controller.mapper;

import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.GetTeacherListRes;
import com.ohdab.member.controller.response.JoinRes;
import com.ohdab.member.controller.response.LoginRes;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.dto.MemberDtoForLogin;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberWebMapper {

    public static MemberDtoForJoin.Request toJoinReqDto(JoinReq joinReq) {
        return MemberDtoForJoin.Request.builder()
                .name(joinReq.getName())
                .password(joinReq.getPassword())
                .role(joinReq.getRole())
                .build();
    }

    public static MemberDtoForLogin.Request toLoginReqDto(LoginReq loginReq) {
        return MemberDtoForLogin.Request.builder()
                .name(loginReq.getName())
                .password(loginReq.getPassword())
                .build();
    }

    public static LoginRes toLoginRes(MemberDtoForLogin.Response loginResDto) {
        return LoginRes.builder()
                .message("로그인에 성공하였습니다.")
                .memberId(loginResDto.getMemberId())
                .jwtToken(loginResDto.getJwtToken())
                .build();
    }

    public static JoinRes toJoinRes() {
        return JoinRes.builder().message("회원가입이 완료되었습니다.").build();
    }

    public static GetTeacherListRes teacherDtoListToResponseList(
            List<MemberDtoForGetTeacherList.Response> teacherDtoList) {
        return GetTeacherListRes.builder()
                .teachers(
                        teacherDtoList.stream()
                                .map(
                                        t ->
                                                GetTeacherListRes.TeacherInfo.builder()
                                                        .id(t.getId())
                                                        .name(t.getName())
                                                        .authorities(t.getAuthorities())
                                                        .status(t.getStatus())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }
}
