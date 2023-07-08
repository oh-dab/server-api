package com.ohdab.port.in;

import com.ohdab.dto.LoginReqDto;
import com.ohdab.dto.LoginResDto;

public interface LoginUsecase {

    LoginResDto login(LoginReqDto loginReqDto);
}
