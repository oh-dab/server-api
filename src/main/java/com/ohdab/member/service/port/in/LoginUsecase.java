package com.ohdab.member.service.port.in;

import com.ohdab.member.service.dto.LoginReqDto;
import com.ohdab.member.service.dto.LoginResDto;

public interface LoginUsecase {

    LoginResDto login(LoginReqDto loginReqDto);
}
