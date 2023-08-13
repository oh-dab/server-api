package com.ohdab.classroom.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddedStudentEvent {

    private String studentName;
    private String password;

    @Builder
    public AddedStudentEvent(String studentName) {
        this.studentName = studentName;
        this.password = "1234";
    }
}
