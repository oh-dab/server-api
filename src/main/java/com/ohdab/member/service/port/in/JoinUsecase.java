package com.ohdab.member.service.port.in;

import com.ohdab.member.service.dto.JoinReqDto;

public interface JoinUsecase {

    void join(JoinReqDto joinReqDto);
}
