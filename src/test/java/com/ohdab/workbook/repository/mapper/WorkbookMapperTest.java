package com.ohdab.workbook.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.classroom.service.dto.ClassroomDetailDto;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
class WorkbookMapperTest {

    @Autowired private WorkbookMapper workbookMapper;
    @Autowired private WorkbookRepository workbookRepository;
    @Autowired private EntityManager em;

    @DisplayName("반 상세조회시 교재 목록 조회")
    @Test
    void 반_상세조회_교재목록() {
        // given
        final ClassroomId classroomId = new ClassroomId(1L);
        final Workbook workbook1 =
                Workbook.builder()
                        .classroomId(classroomId)
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name("책1")
                                        .description("설명")
                                        .startingNumber(1)
                                        .endingNumber(2000)
                                        .build())
                        .build();
        final Workbook workbook2 =
                Workbook.builder()
                        .classroomId(classroomId)
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name("책2")
                                        .description("설명")
                                        .startingNumber(1)
                                        .endingNumber(2000)
                                        .build())
                        .build();
        final Workbook workbook3 =
                Workbook.builder()
                        .classroomId(classroomId)
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name("책3")
                                        .description("설명")
                                        .startingNumber(1)
                                        .endingNumber(2000)
                                        .build())
                        .build();

        // when
        Workbook savedWorkbook1 = workbookRepository.save(workbook1);
        Workbook savedWorkbook2 = workbookRepository.save(workbook2);
        Workbook savedWorkbook3 = workbookRepository.save(workbook3);
        em.flush();
        em.clear();

        List<ClassroomDetailDto.WorkbookInfoDto> workbooks =
                workbookMapper.findAllWorkbookForClassroomInfo(
                        List.of(
                                savedWorkbook1.getId(),
                                savedWorkbook2.getId(),
                                savedWorkbook3.getId()));

        // then
        assertThat(workbooks)
                .hasSize(3)
                .extracting(
                        ClassroomDetailDto.WorkbookInfoDto::getWorkbookId,
                        ClassroomDetailDto.WorkbookInfoDto::getWorkbookName)
                .contains(tuple(savedWorkbook3.getId(), "책3"))
                .contains(tuple(savedWorkbook2.getId(), "책2"))
                .contains(tuple(savedWorkbook1.getId(), "책1"));
    }
}
