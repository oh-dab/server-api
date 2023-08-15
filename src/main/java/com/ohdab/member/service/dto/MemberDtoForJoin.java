package com.ohdab.member.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtoForJoin {

    @Builder
    @Getter
    public static class Request {
        private String name;
        private String password;
        private List<String> role;
    }
}
