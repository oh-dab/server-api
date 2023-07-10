package com.ohdab.domain.group;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassroomInfo {

    private String name;
    private Grade grade;
}
