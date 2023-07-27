package com.ohdab.mistakenote.service;

import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetMistakeNoteInfoService.class})
class GetMistakeNoteInfoServiceTest {

    @Autowired
    private GetMistakeNoteInfoUsecase getMistakeNoteInfoUsecase;
    @MockBean
    private MistakeNoteRepository mistakeNoteRepository;
    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("학생별 오답노트를 조회할 때 ")
    @Test
    void 학생별_오답노트를_조회할_때() throws Exception {
        // given
        final long workbookId = 1;
        final long studentId = 2;

        // when
        when(memberRepository.findById(anyLong())).thenReturn();

        // then
        assertThatThrownBy(() -> getMistakeNoteInfoUsecase.getMistakeNoteInfoByStudent(workbookId, studentId))
                .;
    }
}