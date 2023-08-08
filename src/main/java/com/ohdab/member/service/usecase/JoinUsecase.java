package com.ohdab.member.service.usecase;

import com.ohdab.member.service.dto.MemberDtoForJoin;

public interface JoinUsecase {

    void join(MemberDtoForJoin.Request joinReqDto);
}
