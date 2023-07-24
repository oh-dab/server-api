package com.ohdab.teacher.domain;

import com.ohdab.teacher.domain.group.Classroom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
