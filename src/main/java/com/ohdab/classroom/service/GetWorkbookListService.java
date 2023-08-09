package com.ohdab.classroom.service;

import com.ohdab.classroom.exception.CannotFindClassroomException;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomWorkbookListDto;
import com.ohdab.classroom.service.usecase.GetWorkbookListUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkbookListService implements GetWorkbookListUsecase {

    private final ClassroomRepository classroomRepository;
    private final WorkbookRepository workbookRepository;

    @Override
    public List<ClassroomWorkbookListDto.Response> getWorkbookListByClassroomId(long classroomId) {
        throwIfUnknownClassroomId(classroomId);
        List<Workbook> workbookList = workbookRepository.findByClassroomId(classroomId);
        return workbookDomainListToWorkbookDtoList(workbookList);
    }

    private void throwIfUnknownClassroomId(long classroomId) {
        if (!classroomRepository.existsById(classroomId)) {
            throw new CannotFindClassroomException(
                    "Cannot find classroom with id \"" + classroomId + "\"");
        }
    }

    private List<ClassroomWorkbookListDto.Response> workbookDomainListToWorkbookDtoList(
            List<Workbook> workbookList) {
        return workbookList.stream()
                .map(
                        workbook ->
                                ClassroomWorkbookListDto.Response.builder()
                                        .id(workbook.getId())
                                        .name(workbook.getWorkbookInfo().getName())
                                        .createdAt(workbook.getCreatedAt())
                                        .build())
                .collect(Collectors.toList());
    }
}
