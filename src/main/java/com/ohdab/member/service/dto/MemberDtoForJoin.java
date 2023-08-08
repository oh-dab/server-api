package com.ohdab.member.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDtoForJoin {

    @Builder
    @Getter
    public static class Request {
        private String name;
        private String password;
        private List<String> role;
    }
}
