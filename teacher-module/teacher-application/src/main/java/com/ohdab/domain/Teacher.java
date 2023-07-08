package com.ohdab.domain;

import com.ohdab.domain.group.Group;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Teacher {

    private Long id;
    private List<Group> classes;
}
