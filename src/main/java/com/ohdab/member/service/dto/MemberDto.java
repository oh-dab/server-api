package com.ohdab.member.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDto {

    private Long id;

    private String name;

    private String password;

    private List<String> authorities;

    private String status;
}
