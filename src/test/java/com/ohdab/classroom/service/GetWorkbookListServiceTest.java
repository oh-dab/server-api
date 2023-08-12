package com.ohdab.classroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.repository.ClassroomRepository;
import com.ohdab.classroom.service.dto.ClassroomWorkbookDto;
import com.ohdab.classroom.service.usecase.GetWorkbookListUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetWorkbookListService.class})
public class GetWorkbookListServiceTest {

    @Autowired private GetWorkbookListUsecase getWorkbookListUsecase;
    @MockBean private ClassroomRepository classroomRepository;
    @MockBean private WorkbookRepository workbookRepository;

    @Test
    @DisplayName("교재 목록 조회 성공 테스트")
    void 교재_목록_조회_성공() {
        // given
        long classroomId = 1L;
        Workbook workbook1 = createWorkbook("교재");
        Workbook workbook2 = createWorkbook("교재2");
        Workbook workbook3 = createWorkbook("교재3");
        List<Workbook> workbookList = new ArrayList<>();
        workbookList.add(workbook1);
        workbookList.add(workbook2);
        workbookList.add(workbook3);

        // when
        when(classroomRepository.existsById(Mockito.anyLong())).thenReturn(true);
        when(workbookRepository.findByClassroomId(Mockito.any())).thenReturn(workbookList);
        List<ClassroomWorkbookDto.Response> results =
                getWorkbookListUsecase.getWorkbookListByClassroomId(classroomId);

        // then
        assertThat(results)
                .hasSize(3)
                .extracting(w -> w.getName())
                .contains(
                        workbook1.getWorkbookInfo().getName(),
                        workbook2.getWorkbookInfo().getName(),
                        workbook3.getWorkbookInfo().getName());
    }

    private Workbook createWorkbook(String name) {
        return Workbook.builder()
                .workbookInfo(
                        WorkbookInfo.builder()
                                .name(name)
                                .description(name + "의 설명입니다.")
                                .startingNumber(1)
                                .endingNumber(2000)
                                .build())
                .classroomId(new ClassroomId(1L))
                .build();
    }
}
