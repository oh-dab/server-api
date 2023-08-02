package com.ohdab.mistakenote.service;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;
import com.ohdab.mistakenote.service.helper.MistakeNoteHelperService;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetMistakeNoteInfoService.class, MistakeNoteHelperService.class})
class GetMistakeNoteInfoServiceTest {

    @Autowired private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;
    @Autowired private MistakeNoteHelperService mistakeNoteHelperService;
    @MockBean private MistakeNoteRepository mistakeNoteRepository;
    @MockBean private MemberRepository memberRepository;

    @DisplayName("교재 Id, 학생 Id를 통해 학생별 오답노트를 조회한다.")
    @Test
    void 학생별_오답노트_조회() throws Exception {
        // given
        final long workbookId = 1;
        final long studentId = 2;

        final List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("TEACHER"));

        final Member findMember =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("test").password("1234").build())
                        .authorities(authorities)
                        .build();

        final Map<Integer, Integer> mistakeRecords = new HashMap<>();
        mistakeRecords.put(1, 2);
        mistakeRecords.put(2, 4);
        mistakeRecords.put(4, 1);

        final MistakeNote mistakeNote =
                MistakeNote.builder()
                        .workbookId(new WorkbookId(workbookId))
                        .studentId(new StudentId(studentId))
                        .mistakeRecords(mistakeRecords)
                        .build();

        // when
        when(memberRepository.findActiveMemberById(anyLong())).thenReturn(Optional.of(findMember));
        when(mistakeNoteRepository.findByWorkbookIdAndStudentId(
                        any(WorkbookId.class), any(StudentId.class)))
                .thenReturn(Optional.of(mistakeNote));
        GetMistakeNoteInfoOfStudentDto.Response result =
                getMistakeNoteInfoUsecase.getMistakeNoteInfoOfStudent(workbookId, studentId);

        // then
        assertThat(result.getMistakeNoteInfo().get(0).getWrongNumber()).isEqualTo(1);
        assertThat(result.getMistakeNoteInfo().get(0).getWrongCount()).isEqualTo(2);
        assertThat(result.getMistakeNoteInfo().get(1).getWrongNumber()).isEqualTo(2);
        assertThat(result.getMistakeNoteInfo().get(1).getWrongCount()).isEqualTo(4);
        assertThat(result.getMistakeNoteInfo().get(2).getWrongNumber()).isEqualTo(4);
        assertThat(result.getMistakeNoteInfo().get(2).getWrongCount()).isEqualTo(1);
    }

    @DisplayName("교재 id를 통해 해당 교재에 대한 모든 학생들의 오답 기록을 조회한다.")
    @Test
    void 교재_상세조회() {
        // given
        final WorkbookId workbookId = new WorkbookId(1L);

        List<MistakeNote> mistakeNotes = new ArrayList<>();

        final Map<Integer, Integer> mistakeRecords2 = new HashMap<>();
        mistakeRecords2.put(1, 2);
        mistakeRecords2.put(2, 2);
        mistakeRecords2.put(3, 2);
        mistakeNotes.add(MistakeNote.builder()
                        .workbookId(workbookId)
                        .studentId(new StudentId(2L))
                        .mistakeRecords(mistakeRecords2)
                .build());

        final Map<Integer, Integer> mistakeNoteRecords3 = new HashMap<>();
        mistakeNoteRecords3.put(1, 2);
        mistakeNoteRecords3.put(2, 2);
        mistakeNoteRecords3.put(3, 2);
        mistakeNotes.add(MistakeNote.builder()
                .workbookId(workbookId)
                .studentId(new StudentId(3L))
                .mistakeRecords(mistakeNoteRecords3)
                .build());

        final Map<Integer, Integer> mistakeRecords4 = new HashMap<>();
        mistakeRecords4.put(1, 2);
        mistakeRecords4.put(2, 2);
        mistakeRecords4.put(3, 2);
        mistakeNotes.add(MistakeNote.builder()
                .workbookId(workbookId)
                .studentId(new StudentId(4L))
                .mistakeRecords(mistakeRecords4)
                .build());

        // when
        when(mistakeNoteRepository.findByWorkbookId(any(WorkbookId.class)))
                .thenReturn(mistakeNotes);

        // then
        assertThatNoException().isThrownBy(() -> getMistakeNoteInfoUsecase.getAllMistakeNoteInfo(workbookId.getId()));
    }
}
