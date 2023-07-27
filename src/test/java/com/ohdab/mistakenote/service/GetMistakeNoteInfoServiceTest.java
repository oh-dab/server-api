package com.ohdab.mistakenote.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetMistakeNoteInfoService.class})
class GetMistakeNoteInfoServiceTest {

    @Autowired private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;
    @MockBean private MistakeNoteRepository mistakeNoteRepository;
    @MockBean private MemberRepository memberRepository;

    @DisplayName("교재 Id, 학생 Id를 통해 학생별 오답노트를 조회한다.")
    @Test
    void 학생별_오답노트_조회() throws Exception {
        // given
        final long workbookId = 1;
        final long studentId = 2;

        final Map<Integer, Integer> mistakeRecords = new HashMap<>();
        mistakeRecords.put(1, 2);
        mistakeRecords.put(2, 4);
        mistakeRecords.put(4, 1);

        MistakeNote mistakeNote =
                MistakeNote.builder()
                        .workbookId(new WorkbookId(workbookId))
                        .studentId(new StudentId(studentId))
                        .mistakeRecords(mistakeRecords)
                        .build();

        final List<MistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        mistakeRecords.forEach(
                (key, value) -> {
                    mistakeNoteInfo.add(
                            MistakeNoteInfoDto.builder()
                                    .wrongNumber(key)
                                    .wrongCount(value)
                                    .build());
                });

        // when
        when(mistakeNoteRepository.findByWorkbookIdAndStudentId(
                        any(WorkbookId.class), any(StudentId.class)))
                .thenReturn(Optional.ofNullable(mistakeNote));
        List<MistakeNoteInfoDto> result =
                getMistakeNoteInfoUsecase.getMistakeNoteInfoByStudent(workbookId, studentId);

        // then
        assertThat(result.get(0).getWrongNumber())
                .isEqualTo(mistakeNoteInfo.get(0).getWrongNumber());
        assertThat(result.get(0).getWrongCount()).isEqualTo(mistakeNoteInfo.get(0).getWrongCount());

        assertThat(result.get(1).getWrongNumber())
                .isEqualTo(mistakeNoteInfo.get(1).getWrongNumber());
        assertThat(result.get(1).getWrongCount()).isEqualTo(mistakeNoteInfo.get(1).getWrongCount());

        assertThat(result.get(2).getWrongNumber())
                .isEqualTo(mistakeNoteInfo.get(2).getWrongNumber());
        assertThat(result.get(2).getWrongCount()).isEqualTo(mistakeNoteInfo.get(2).getWrongCount());
    }
}
