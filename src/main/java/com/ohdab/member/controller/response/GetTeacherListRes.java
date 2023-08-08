package com.ohdab.member.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetTeacherListRes {

    List<TeacherInfo> teachers;

    @Builder
    @Getter
    public static class TeacherInfo {
        long id;
        String name;
        List<String> authorities;
        String status;
    }
}
