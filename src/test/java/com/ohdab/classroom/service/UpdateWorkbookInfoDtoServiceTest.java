package com.ohdab.classroom.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.service.dto.ClassroomWorkbookUpdateDto;
import com.ohdab.classroom.service.usecase.UpdateWorkbookInfoUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UpdateWorkbookInfoService.class})
class UpdateWorkbookInfoDtoServiceTest {

    @Autowired private UpdateWorkbookInfoUsecase updateWorkbookInfoUsecase;
    @MockBean private WorkbookRepository workbookRepository;

    @Test
    @DisplayName("교재 정보 수정 성공 테스트")
    void 교재_정보_수정_성공() {
        // given
        long classroomId = 1L;
        long workbookId = 2L;
        String name = "수정된 교재명";
        String description = "수정된 교재 정보입니다.";
        ClassroomWorkbookUpdateDto.Request updateWorkbookReq =
                ClassroomWorkbookUpdateDto.Request.builder()
                        .id(workbookId)
                        .name(name)
                        .description(description)
                        .build();
        Workbook workbook =
                Workbook.builder()
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name("교재")
                                        .description("교재 정보입니다.")
                                        .startingNumber(1)
                                        .endingNumber(2000)
                                        .build())
                        .classroomId(new ClassroomId(classroomId))
                        .build();

        // when
        when(workbookRepository.existsById(Mockito.anyLong())).thenReturn(true);
        when(workbookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(workbook));

        // then
        assertThatNoException()
                .isThrownBy(() -> updateWorkbookInfoUsecase.updateWorkbookInfo(updateWorkbookReq));
    }
}
