package com.ohdab.member.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
