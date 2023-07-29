package com.ohdab.mistakenote.service;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.helper.MistakeNoteHelperService;
import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SaveMistakeNoteInfoService.class, MistakeNoteHelperService.class})
class SaveMistakeNoteInfoServiceTest {

    @Autowired
    private SaveMistakeNoteInfoUsecase saveMistakeNoteInfoUsecase;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private MistakeNoteRepository mistakeNoteRepository;

    @DisplayName("틀린 문제의 번호들을 입력하여 학생의 오답을 기록한다.")
    @Test
    void 오답_기록하기() {
        // given
        final long workbookId = 1L;
        final long studentId = 2L;

        final List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("STUDENT"));
        final Member findMember =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("test").password("1234").build())
                        .authorities(authorities)
                        .build();

        final List<Integer> mistakeNumbers = new ArrayList<>();
        mistakeNumbers.add(1);
        mistakeNumbers.add(2);
        mistakeNumbers.add(3);
        mistakeNumbers.add(5);
        final SaveMistakeNoteInfoDto saveMistakeNoteInfoDto = SaveMistakeNoteInfoDto.builder()
                .workbookId(workbookId)
                .studentId(studentId)
                .mistakeNumbers(mistakeNumbers)
                .build();

        final Map<Integer, Integer> mistakeRecords = new HashMap<>();
        mistakeRecords.put(1, 2);
        mistakeRecords.put(2, 4);
        mistakeRecords.put(4, 1);
        final MistakeNote mistakeNote = MistakeNote.builder()
                .workbookId(new WorkbookId(workbookId))
                .studentId(new StudentId(studentId))
                .mistakeRecords(mistakeRecords)
                .build();

        // when
        when(memberRepository.findActiveMemberById(anyLong())).thenReturn(Optional.of(findMember));
        when(mistakeNoteRepository.findByWorkbookIdAndStudentId(any(WorkbookId.class), any(StudentId.class)))
                .thenReturn(Optional.of(mistakeNote));

        // then
        assertDoesNotThrow(() -> saveMistakeNoteInfoUsecase.saveMistakeNoteInfo(saveMistakeNoteInfoDto));
    }
}