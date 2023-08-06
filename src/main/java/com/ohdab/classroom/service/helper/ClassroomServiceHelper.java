package com.ohdab.classroom.service.helper;

import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.exception.CannotFindGradeException;

public class ClassroomServiceHelper {

    public static Grade findGradeByString(String stringGrade) {
        try {
            return Grade.valueOfLabel(stringGrade);
        } catch (Exception e) {
            throw new CannotFindGradeException("Cannot find Grade : " + stringGrade, e);
        }
    }
}
