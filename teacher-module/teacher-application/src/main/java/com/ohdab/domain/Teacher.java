package com.ohdab.domain;

import com.ohdab.domain.group.Classroom;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Teacher {

    private Long id;
    private List<Classroom> classrooms;
}
