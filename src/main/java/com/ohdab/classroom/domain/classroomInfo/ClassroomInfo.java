package com.ohdab.classroom.domain.classroomInfo;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomInfo {

    private String name;
    private String description;
    private Grade grade;

    @Builder
    public ClassroomInfo(String name, String description, Grade grade) {
        setName(name);
        setDescription(description);
        setGrade(grade);
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \"" + name.length() + "\"");
        }
        this.name = name;
    }

    private void setDescription(String description) {
        if (description.length() > 30) {
            throw new IllegalStateException(
                    "Description length cannot exceed 30 : current length \""
                            + name.length()
                            + "\"");
        }
        this.description = description;
    }

    private void setGrade(Grade grade) {
        this.grade = grade;
    }
}
