package com.ohdab.member.application.service.spi;

import com.ohdab.member.application.service.dto.LoginReqDto;
import com.ohdab.member.application.service.dto.LoginResDto;

public interface LoginService {

    LoginResDto login(LoginReqDto loginReqDto);
}
