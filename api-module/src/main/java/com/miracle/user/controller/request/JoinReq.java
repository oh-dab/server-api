package com.miracle.user.controller.request;

import com.miracle.user.service.dto.JoinReqDto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinReq {

    private String userId;
    private String password;

    public static JoinReqDto toDto(JoinReq joinReq) {
        return JoinReqDto.builder()
                .userId(joinReq.getUserId())
                .password(joinReq.getPassword())
                .build();
    }
}
