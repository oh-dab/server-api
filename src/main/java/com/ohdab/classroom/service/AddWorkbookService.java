package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.Classroom;
import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.exception.DuplicatedWorkbookException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto.Request;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.classroom.service.usecase.AddWorkbookUsecase;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddWorkbookService implements AddWorkbookUsecase {

    private final ClassroomRepository classroomRepository;
    private final WorkbookRepository workbookRepository;
    private final MistakeNoteRepository mistakeNoteRepository;

    @Override
    public void addWorkbookByClassroomId(long classroomId, Request addWorkbookDto) {
        Classroom classroom =
                ClassroomHelperService.findExistingClassroom(classroomId, classroomRepository);
        ClassroomId classroomId1 = new ClassroomId(classroomId);
        throwIfDuplicatedWorkbookName(classroomId1, addWorkbookDto.getName());
        Workbook workbook = saveWorkbook(classroomId1, addWorkbookDto);
        WorkbookId workbookId = new WorkbookId(workbook.getId());
        classroom.addWorkbook(workbookId);
        saveMistakeNote(classroomId, workbookId);
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

    private Workbook saveWorkbook(
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
        return workbookRepository.save(workbook);
    }

    private void saveMistakeNote(long classroomId, WorkbookId workbookId) {
        List<StudentId> studentIdList = classroomRepository.findStudentsById(classroomId);
        studentIdList.forEach(
                studentId -> {
                    MistakeNote mistakeNote =
                            MistakeNote.builder()
                                    .studentId(studentId)
                                    .workbookId(workbookId)
                                    .mistakeRecords(new HashMap<>())
                                    .build();
                    mistakeNoteRepository.save(mistakeNote);
                });
    }
}
