package com.ohdab.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDtoForLogin {

    @Getter
    @Builder
    public static class Request {

        private String name;
        private String password;
    }

    @Getter
    @Builder
    public static class Response {
        private Long memberId;
        private String jwtToken;
    }
}
