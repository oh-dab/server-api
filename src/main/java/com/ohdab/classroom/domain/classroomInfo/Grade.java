package com.ohdab.classroom.domain.classroomInfo;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Grade {
    MID_1("mid1"),
    MID_2("mid2"),
    MID_3("mid3"),
    HIGH_1("high1"),
    HIGH_2("high2"),
    HIGH_3("high3");

    public final String label;

    private Grade(String label){
        this.label = label;
    }

    private static final Map<String, Grade> BY_LABEL = new HashMap<>();

    static {
        for (Grade e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public static Grade valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

}
