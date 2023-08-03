package com.ohdab.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDtoForAddTeacher {

    @Builder
    @Getter
    public static class Request {

        private String name;
    }
}
