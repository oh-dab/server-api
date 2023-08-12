package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.exception.DuplicatedWorkbookException;
import com.ohdab.classroom.exception.InvalidWorkbookNumberRangeException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto.Request;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.classroom.service.usecase.AddWorkbookUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import com.ohdab.workbook.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddWorkbookService implements AddWorkbookUsecase {

    private final ClassroomRepository classroomRepository;
    private final WorkbookRepository workbookRepository;

    @Override
    public void addWorkbookByClassroomId(long classroomId, Request addWorkbookDto) {
        Classroom classroom =
                ClassroomHelperService.findExistingClassroom(classroomId, classroomRepository);
        ClassroomId classroomId1 = new ClassroomId(classroomId);
        throwIfDuplicatedWorkbookName(classroomId1, addWorkbookDto.getName());
        throwIfInvalidRange(addWorkbookDto.getStartNumber(), addWorkbookDto.getEndNumber());
        saveWorkbook(classroomId1, addWorkbookDto);
        WorkbookId workbookId =
                new WorkbookId(
                        workbookRepository.findIdByClassroomIdAndWorkbookInfoName(
                                classroomId1, addWorkbookDto.getName()));
        classroom.addWorkbook(workbookId);
    }

    private void throwIfDuplicatedWorkbookName(ClassroomId classroomId, String name) {
        if (workbookRepository.existsByClassroomIdAndWorkbookInfoName(classroomId, name)) {
            throw new DuplicatedWorkbookException(
                    "Duplicated workbook name \""
                            + name
                            + "\" in classroom with id \""
                            + classroomId
                            + "\"");
        }
    }

    private void throwIfInvalidRange(int startNumber, int endNumber) {
        if (startNumber > endNumber || 1 > endNumber || 5000 < endNumber) {
            throw new InvalidWorkbookNumberRangeException(
                    "Invalid range with starting number \""
                            + startNumber
                            + "\", ending number \""
                            + endNumber
                            + "\"");
        }
    }

    private void saveWorkbook(
            ClassroomId classroomId, ClassroomAddWorkbookDto.Request addWorkbookDto) {
        Workbook workbook =
                Workbook.builder()
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name(addWorkbookDto.getName())
                                        .description(addWorkbookDto.getDescription())
                                        .startingNumber(addWorkbookDto.getStartNumber())
                                        .endingNumber(addWorkbookDto.getEndNumber())
                                        .build())
                        .classroomId(classroomId)
                        .build();
        workbookRepository.save(workbook);
    }
}
