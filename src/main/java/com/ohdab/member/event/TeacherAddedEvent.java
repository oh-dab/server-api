package com.ohdab.member.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherAddedEvent {

    private String name;
    private String password;

    @Builder
    public TeacherAddedEvent(String name) {
        this.name = name;
        this.password = "1234";
    }
}
