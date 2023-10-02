package com.ohdab.classroom.service.helper;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomInfo.Grade;
import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.exception.NoClassroomException;
import com.ohdab.classroom.exception.NoGradeException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.workbook.exception.DuplicatedWorkbookException;
import com.ohdab.workbook.repository.WorkbookRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ClassroomHelperService {

    public static Grade findGradeByString(String stringGrade) {
        try {
            return Grade.valueOfLabel(stringGrade);
        } catch (NullPointerException e) {
            throw new NoGradeException(ExceptionEnum.NO_GRADE.getMessage());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Classroom findExistingClassroom(
            long classroomId, ClassroomRepository classroomRepository) {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow(
                        () -> new NoClassroomException(ExceptionEnum.NO_CLASSROOM.getMessage()));
    }

    public static void throwIfDuplicatedWorkbookName(
            WorkbookRepository workbookRepository, ClassroomId classroomId, String name) {
        if (workbookRepository.existsByClassroomIdAndWorkbookInfoName(classroomId, name)) {
            throw new DuplicatedWorkbookException(ExceptionEnum.DUPLICATED_WORKBOOK.getMessage());
        }
    }
}
