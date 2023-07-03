package com.miracle.user.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinReqDto {

    private String userId;
    private String password;

    @Builder
    public JoinReqDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
