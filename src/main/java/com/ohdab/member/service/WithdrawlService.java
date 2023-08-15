package com.ohdab.member.service;

import com.ohdab.member.service.usecase.WithdrawlUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawlService implements WithdrawlUsecase {

    @Override
    public void withdrawl(long memberId) {}
}
