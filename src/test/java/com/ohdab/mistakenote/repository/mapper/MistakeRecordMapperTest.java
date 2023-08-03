package com.ohdab.mistakenote.repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureMybatis
class MistakeRecordMapperTest {

    @Autowired private MistakeRecordMapper mistakeRecordMapper;
    @Autowired private MistakeNoteRepository mistakeNoteRepository;
    @Autowired private EntityManager em;

    @DisplayName("전체 학생에 대한 오답노트를 조회한다.")
    @Test
    void 교재_상세조회() {
        // given
        final WorkbookId workbookId = new WorkbookId(10L);

        final StudentId studentId1 = new StudentId(1L);
        final Map<Integer, Integer> mistakeRecords1 = new HashMap<>();
        mistakeRecords1.put(1, 1);
        mistakeRecords1.put(2, 2);
        mistakeRecords1.put(3, 3);
        final MistakeNote mistakeNote1 =
                MistakeNote.builder()
                        .workbookId(workbookId)
                        .studentId(studentId1)
                        .mistakeRecords(mistakeRecords1)
                        .build();

        final StudentId studentId2 = new StudentId(2L);
        final Map<Integer, Integer> mistakeRecords2 = new HashMap<>();
        mistakeRecords2.put(3, 1);
        mistakeRecords2.put(4, 2);
        mistakeRecords2.put(5, 1);
        final MistakeNote mistakeNote2 =
                MistakeNote.builder()
                        .workbookId(workbookId)
                        .studentId(studentId2)
                        .mistakeRecords(mistakeRecords2)
                        .build();

        final StudentId studentId3 = new StudentId(3L);
        final Map<Integer, Integer> mistakeRecords3 = new HashMap<>();
        mistakeRecords3.put(1, 1);
        mistakeRecords3.put(102, 1);
        mistakeRecords3.put(110, 2);
        final MistakeNote mistakeNote3 =
                MistakeNote.builder()
                        .workbookId(workbookId)
                        .studentId(studentId3)
                        .mistakeRecords(mistakeRecords3)
                        .build();

        MistakeNote savedMistakeNote1 = mistakeNoteRepository.save(mistakeNote1);
        MistakeNote savedMistakeNote2 = mistakeNoteRepository.save(mistakeNote2);
        MistakeNote savedMistakeNote3 = mistakeNoteRepository.save(mistakeNote3);

        em.flush();
        em.clear();

        // when
        List<AllMistakeNoteInfoDto> result =
                mistakeRecordMapper.findAllMistakeNoteInfo(
                        List.of(
                                savedMistakeNote1.getId(),
                                savedMistakeNote2.getId(),
                                savedMistakeNote3.getId()));

        // then
        assertThat(result)
                .extracting("wrongNumber", "wrongStudentsCount")
                .contains(tuple(1, 2))
                .contains(tuple(2, 1))
                .contains(tuple(3, 2))
                .contains(tuple(4, 1))
                .contains(tuple(5, 1))
                .contains(tuple(102, 1))
                .contains(tuple(110, 1));
    }
}
