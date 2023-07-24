package com.ohdab.domain.student;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Student {

    private String name;

    public Student(String name) {
        setName(name);
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \"" + name.length() + "\"");
        }
        this.name = name;
    }
}
