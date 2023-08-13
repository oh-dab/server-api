package com.ohdab.member.controller.mistakenote.repository;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MistakeNoteRepositoryTest {

    @Autowired private MistakeNoteRepository mistakeNoteRepository;
    @Autowired private EntityManager em;

    @DisplayName("교재 id와 학생 id를 통해 오답노트를 조회한다.")
    @Test
    void 학생별_오답노트_조회() {
        // given
        final StudentId studentId = new StudentId(1L);
        final WorkbookId workbookId = new WorkbookId(2L);

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
        assertThat(result.getMistakeRecords())
                .containsEntry(1, 2)
                .containsEntry(2, 4)
                .containsEntry(4, 1);
    }

    @DisplayName("변경감지를 활용하여 틀린 문제 번호마다 틀린 횟수를 저장한다.")
    @Test
    void 오답_기록하기() {
        // given
        final List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(5);

        final StudentId studentId = new StudentId(1L);
        final WorkbookId workbookId = new WorkbookId(2L);

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

        MistakeNote savedMistakeNote = mistakeNoteRepository.save(mistakeNote);
        Map<Integer, Integer> savedRecords = savedMistakeNote.getMistakeRecords();

        // when
        numbers.forEach(
                number -> {
                    if (savedRecords.containsKey(number)) {
                        savedRecords.put(number, savedRecords.get(number) + 1);
                    } else {
                        savedRecords.put(number, 1);
                    }
                });
        em.flush();
        em.clear();

        // then
        MistakeNote result = mistakeNoteRepository.findById(savedMistakeNote.getId()).get();
        Map<Integer, Integer> resultRecords = result.getMistakeRecords();
        assertThat(resultRecords)
                .containsEntry(1, 3)
                .containsEntry(2, 5)
                .containsEntry(3, 1)
                .containsEntry(4, 1)
                .containsEntry(5, 1);
    }

    @Test
    @DisplayName("오답 노트 생성 성공 테스트")
    void 오답_노트_생성_성공_테스트() {
        // given
        MistakeNote mistakeNote =
                MistakeNote.builder()
                        .studentId(new StudentId(1L))
                        .workbookId(new WorkbookId(2L))
                        .build();

        // when
        mistakeNote = mistakeNoteRepository.save(mistakeNote);

        // then
        assertThat(mistakeNote)
                .extracting(
                        m -> m.getId(),
                        m -> m.getWorkbookId().getId(),
                        m -> m.getStudentId().getId(),
                        m -> m.getMistakeRecords().size())
                .containsExactly(
                        mistakeNote.getId(),
                        mistakeNote.getWorkbookId().getId(),
                        mistakeNote.getStudentId().getId(),
                        mistakeNote.getMistakeRecords().size());
    }
}
