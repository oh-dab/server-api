package com.ohdab.classroom.service.helper;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.exception.DuplicatedWorkbookException;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.exception.NoGradeException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.workbook.repository.WorkbookRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ClassroomHelperService {

    public static Grade findGradeByString(String stringGrade) {
        try {
            return Grade.valueOfLabel(stringGrade);
        } catch (NullPointerException e) {
            throw new NoGradeException("Cannot find Grade : " + stringGrade, e);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("인자의 자료형이 잘못되었습니다.");
        }
    }

    public static Classroom findExistingClassroom(
            long classroomId, ClassroomRepository classroomRepository) {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow(
                        () ->
                                new NoClassroomException(
                                        "반을 찾을 수 없습니다. classroomId: " + classroomId));
    }

    public static void throwIfDuplicatedWorkbookName(
            WorkbookRepository workbookRepository, ClassroomId classroomId, String name) {
        if (workbookRepository.existsByClassroomIdAndWorkbookInfoName(classroomId, name)) {
            throw new DuplicatedWorkbookException(
                    "Duplicated workbook name \""
                            + name
                            + "\" in classroom with id \""
                            + classroomId
                            + "\"");
        }
    }
}
