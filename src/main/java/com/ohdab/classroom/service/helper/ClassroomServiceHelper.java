package com.ohdab.classroom.service.helper;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.exception.NoGradeException;
import com.ohdab.classroom.repository.ClassroomRepository;

public class ClassroomServiceHelper {

    public static Grade findGradeByString(String stringGrade) {
        try {
            return Grade.valueOfLabel(stringGrade);
        } catch (Exception e) {
            throw new NoGradeException("Cannot find Grade : " + stringGrade, e);
        }
    }

    public static Classroom getClassroomById(long id, ClassroomRepository classroomRepository) {
        try {
            return classroomRepository.findClassroomById(id);
        } catch (Exception e) {
            throw new NoClassroomException("반을 찾을 수 없습니다. id : " + id, e);
        }
    }
}
