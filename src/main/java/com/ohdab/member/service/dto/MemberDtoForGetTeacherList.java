package com.ohdab.member.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDtoForGetTeacherList {

    @Builder
    @Getter
    public static class Response {

        private Long id;

        private String name;

        private List<String> authorities;

        private String status;
    }
}
