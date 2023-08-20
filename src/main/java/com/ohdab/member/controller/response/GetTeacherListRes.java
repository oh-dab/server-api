package com.ohdab.member.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetTeacherListRes {

    private List<TeacherInfo> teachers;

    @Builder
    @Getter
    public static class TeacherInfo {
        private long id;
        private String name;
        private List<String> authorities;
        private String status;
    }
}
