package com.ohdab.member.controller.request;

import com.ohdab.member.application.service.dto.JoinReqDto;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinReq {

    @NotNull(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    public static JoinReqDto toDto(JoinReq joinReq) {
        return JoinReqDto.builder().name(joinReq.getName()).password(joinReq.getPassword()).build();
    }
}
