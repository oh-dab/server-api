package com.ohdab.member.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtoForAddTeacher {

    @Builder
    @Getter
    public static class Request {

        private String name;
    }
}
