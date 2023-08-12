package com.ohdab.classroom.service;

import com.ohdab.classroom.service.dto.ClassroomWorkbookUpdateDto.Request;
import com.ohdab.classroom.service.helper.ClassroomHelperService;
import com.ohdab.classroom.service.usecase.UpdateWorkbookInfoUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.exception.NoWorkbookException;
import com.ohdab.workbook.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UpdateWorkbookInfoService implements UpdateWorkbookInfoUsecase {

    private final WorkbookRepository workbookRepository;

    @Override
    public void updateWorkbookInfo(Request updateWorkbookReq) {
        throwIfUnknownWorkbookId(updateWorkbookReq.getId());
        Workbook workbook = workbookRepository.findById(updateWorkbookReq.getId()).get();
        ClassroomHelperService.throwIfDuplicatedWorkbookName(
                workbookRepository, workbook.getClassroomId(), updateWorkbookReq.getName());
        workbook.updateWorkbookInfo(setWorkbookInfo(updateWorkbookReq, workbook));
        workbookRepository.save(workbook);
    }

    private void throwIfUnknownWorkbookId(long id) {
        if (!workbookRepository.existsById(id)) {
            throw new NoWorkbookException("No Workbook with id \"" + id + "\"");
        }
    }

    private WorkbookInfo setWorkbookInfo(Request updateWorkbookReq, Workbook workbook) {
        return WorkbookInfo.builder()
                .name(updateWorkbookReq.getName())
                .description(updateWorkbookReq.getDescription())
                .startingNumber(workbook.getWorkbookInfo().getStartingNumber())
                .endingNumber(workbook.getWorkbookInfo().getEndingNumber())
                .build();
    }
}
