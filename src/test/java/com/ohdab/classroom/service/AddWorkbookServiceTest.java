package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomAddWorkbookDto;
import com.ohdab.classroom.service.usecase.AddWorkbookUsecase;
import com.ohdab.workbook.repository.WorkbookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AddWorkbookService.class})
public class AddWorkbookServiceTest {

    @Autowired private AddWorkbookUsecase addWorkbookUsecase;
    @MockBean private WorkbookRepository workbookRepository;
    @MockBean private ClassroomRepository classroomRepository;

    @Test
    @DisplayName("교재 추가 성공 테스트")
    void 교재_추가_성공() {
        // given
        ClassroomAddWorkbookDto.Request addWorkbookDto =
                ClassroomAddWorkbookDto.Request.builder()
                        .classroomId(1L)
                        .name("교재")
                        .description("교재 설명입니다.")
                        .startNumber(1)
                        .endNumber(2000)
                        .build();

        // when
        when(classroomRepository.existsById(Mockito.anyLong())).thenReturn(true);
        when(workbookRepository.findByClassroomId(Mockito.any())).thenReturn(null);
        when(workbookRepository.existsByWorkbookInfoName(Mockito.any())).thenReturn(false);

        // then
        assertThatNoException().isThrownBy(() -> addWorkbookUsecase.addWorkbook(addWorkbookDto));
    }
}
