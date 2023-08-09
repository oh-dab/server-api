package com.ohdab.classroom.service;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.exception.CannotFindClassroomException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomWorkbookDto;
import com.ohdab.classroom.service.usecase.GetWorkbookListUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkbookListService implements GetWorkbookListUsecase {

    @Autowired private final ClassroomRepository classroomRepository;
    @Autowired private final WorkbookRepository workbookRepository;

    @Override
    public List<ClassroomWorkbookDto.Response> getWorkbookListByClassroomId(long classroomId) {
        throwIfUnknownClassroomId(classroomId);
        List<Workbook> workbookList =
                workbookRepository.findByClassroomId(new ClassroomId(classroomId));
        return workbookList.stream()
                .map(
                        workbook ->
                                ClassroomWorkbookDto.Response.builder()
                                        .id(workbook.getId())
                                        .name(workbook.getWorkbookInfo().getName())
                                        .createdAt(workbook.getCreatedAt())
                                        .build())
                .collect(Collectors.toList());
    }

    private void throwIfUnknownClassroomId(long classroomId) {
        if (!classroomRepository.existsById(classroomId)) {
            throw new CannotFindClassroomException(
                    "Cannot find classroom with id \"" + classroomId + "\"");
        }
    }
}
