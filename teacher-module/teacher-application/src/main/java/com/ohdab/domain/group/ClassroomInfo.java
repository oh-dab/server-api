package com.ohdab.domain.group;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClassroomInfo {

    private String name;
    private Grade grade;

    @Builder
    public ClassroomInfo(String name, Grade grade) {
        setName(name);
        this.grade = grade;
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \"" + name.length() + "\"");
        }
    }
}
