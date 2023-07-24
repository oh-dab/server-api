package com.ohdab.domain;

import com.ohdab.domain.group.Classroom;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Teacher {

    private long id;

    private String name;
    private List<Classroom> classrooms;

    @Builder
    public Teacher(String name) {
        setName(name);
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \"" + name.length() + "\"");
        }
        this.name = name;
    }

    public void addClassroom(Classroom classroom) {
        if (classroom == null) {
            throw new IllegalStateException("Classroom cannot be null.");
        }
        this.classrooms.add(classroom);
    }
}
