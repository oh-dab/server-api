package com.ohdab.member.service.usecase;

import com.ohdab.member.service.dto.MemberDtoForLogin;

public interface LoginUsecase {

    MemberDtoForLogin.Response login(MemberDtoForLogin.Request loginReqDto);
}
