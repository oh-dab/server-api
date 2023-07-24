package com.ohdab.member.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinReqDto {

    private String name;
    private String password;
    private List<String> role;
}
