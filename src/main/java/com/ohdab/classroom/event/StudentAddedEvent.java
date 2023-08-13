package com.ohdab.classroom.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentAddedEvent {

    private String studentName;
    private String password;

    @Builder
    public StudentAddedEvent(String studentName) {
        this.studentName = studentName;
        this.password = "1234";
    }
}
