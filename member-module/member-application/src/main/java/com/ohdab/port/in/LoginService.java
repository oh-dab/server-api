package com.ohdab.port.in;

import com.ohdab.dto.LoginReqDto;
import com.ohdab.dto.LoginResDto;

public interface LoginService {

    LoginResDto login(LoginReqDto loginReqDto);
}
