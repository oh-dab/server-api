package com.ohdab.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {

    private Long memberId;
    private String jwtToken;
}
