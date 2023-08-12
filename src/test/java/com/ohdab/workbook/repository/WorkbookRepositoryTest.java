package com.ohdab.workbook.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WorkbookRepositoryTest {

    @Autowired private WorkbookRepository workbookRepository;

    @Autowired private EntityManager entityManager;

    @Test
    @DisplayName("반 식별자로 반에 있는 교재 목록 조회 성공 테스트")
    void 반_식별자로_반에_있는_교재_목록_조회_성공() {
        // given
        long classroomId = 1L;
        Workbook workbook1 = createAndSaveWorkbook("교재", classroomId);
        Workbook workbook2 = createAndSaveWorkbook("교재2", classroomId);
        Workbook workbook3 = createAndSaveWorkbook("교재3", 2L);

        // when
        List<Workbook> results = workbookRepository.findByClassroomId(new ClassroomId(classroomId));

        // then
        assertThat(results)
                .hasSize(2)
                .extracting(
                        Workbook::getId,
                        w -> w.getClassroomId().getId(),
                        w -> w.getWorkbookInfo().getName(),
                        w -> w.getWorkbookInfo().getDescription(),
                        w -> w.getWorkbookInfo().getStartingNumber(),
                        w -> w.getWorkbookInfo().getEndingNumber(),
                        w ->
                                w.getCreatedAt()
                                        .format(
                                                DateTimeFormatter.ofPattern(
                                                        "uuuu-MM-dd'T'HH:mm:ss")))
                .contains(
                        tuple(
                                workbook1.getId(),
                                workbook1.getClassroomId().getId(),
                                workbook1.getWorkbookInfo().getName(),
                                workbook1.getWorkbookInfo().getDescription(),
                                workbook1.getWorkbookInfo().getStartingNumber(),
                                workbook1.getWorkbookInfo().getEndingNumber(),
                                workbook1
                                        .getCreatedAt()
                                        .format(
                                                DateTimeFormatter.ofPattern(
                                                        "uuuu-MM-dd'T'HH:mm:ss"))),
                        tuple(
                                workbook2.getId(),
                                workbook2.getClassroomId().getId(),
                                workbook2.getWorkbookInfo().getName(),
                                workbook2.getWorkbookInfo().getDescription(),
                                workbook2.getWorkbookInfo().getStartingNumber(),
                                workbook2.getWorkbookInfo().getEndingNumber(),
                                workbook2
                                        .getCreatedAt()
                                        .format(
                                                DateTimeFormatter.ofPattern(
                                                        "uuuu-MM-dd'T'HH:mm:ss"))));
    }

    private Workbook createAndSaveWorkbook(String name, long classroomId) {
        return workbookRepository.save(
                Workbook.builder()
                        .workbookInfo(
                                WorkbookInfo.builder()
                                        .name(name)
                                        .description(name + "의 설명입니다.")
                                        .startingNumber(1)
                                        .endingNumber(2000)
                                        .build())
                        .classroomId(new ClassroomId(classroomId))
                        .build());
    }
}
