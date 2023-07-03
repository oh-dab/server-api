package com.miracle.user.service;

import com.miracle.user.service.dto.JoinReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl implements JoinUserService {

    @Override
    public void join(JoinReqDto joinReqDto) {
        System.out.println("안녕 친구들!");
    }
}
