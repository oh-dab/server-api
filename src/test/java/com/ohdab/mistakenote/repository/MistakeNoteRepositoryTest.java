package com.ohdab.mistakenote.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableConfigurationProperties
class MistakeNoteRepositoryTest {

    @Autowired private MistakeNoteRepository mistakeNoteRepository;
    @Autowired private EntityManager em;

    @DisplayName("교재 id와 학생 id를 통해 오답노트를 조회한다.")
    @Test
    void 학생별_오답노트_조회() {
        // given
        final StudentId studentId = new StudentId(1);
        final WorkbookId workbookId = new WorkbookId(2);

        final Map<Integer, Integer> mistakeRecords = new HashMap<>();
        mistakeRecords.put(1, 2);
        mistakeRecords.put(2, 4);
        mistakeRecords.put(4, 1);

        final MistakeNote mistakeNote =
                MistakeNote.builder()
                        .workbookId(workbookId)
                        .studentId(studentId)
                        .mistakeRecords(mistakeRecords)
                        .build();

        mistakeNoteRepository.save(mistakeNote);
        em.flush();
        em.clear();

        // when
        MistakeNote result =
                mistakeNoteRepository.findByWorkbookIdAndStudentId(workbookId, studentId).get();

        // then
        assertThat(result.getWorkbookId().getId()).isEqualTo(workbookId.getId());
        assertThat(result.getStudentId().getId()).isEqualTo(studentId.getId());
        assertThat(result.getMistakeRecords()).containsEntry(1, 2);
        assertThat(result.getMistakeRecords()).containsEntry(2, 4);
        assertThat(result.getMistakeRecords()).containsEntry(4, 1);
    }
}
