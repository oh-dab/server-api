package com.ohdab.mistakenote.service;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.exception.NoMistakeNoteException;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.helper.MistakeNoteHelperService;
import com.ohdab.mistakenote.service.usecase.SaveMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.service.NumberScopeCheckService;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import com.ohdab.workbook.exception.NoWorkbookException;
import com.ohdab.workbook.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SaveMistakeNoteInfoService implements SaveMistakeNoteInfoUsecase {

    private final MistakeNoteHelperService mistakeNoteHelperService;
    private final NumberScopeCheckService numberScopeCheckService;
    private final MemberRepository memberRepository;
    private final MistakeNoteRepository mistakeNoteRepository;
    private final WorkbookRepository workbookRepository;

    @Override
    public void saveMistakeNoteInfo(SaveMistakeNoteInfoDto saveMistakeNoteInfoDto) {
        if (mistakeNoteHelperService.isNotExistingMember(
                memberRepository, saveMistakeNoteInfoDto.getStudentId())) {
            throw new NoMemberException("존재하지 않는 회원입니다.");
        }
        MistakeNote mistakeNote =
                mistakeNoteRepository
                        .findByWorkbookIdAndStudentId(
                                new WorkbookId(saveMistakeNoteInfoDto.getWorkbookId()),
                                new StudentId(saveMistakeNoteInfoDto.getStudentId()))
                        .orElseThrow(() -> new NoMistakeNoteException("존재하지 않는 오답노트입니다."));
        Workbook workbook =
                workbookRepository
                        .findById(saveMistakeNoteInfoDto.getWorkbookId())
                        .orElseThrow(() -> new NoWorkbookException("존재하지 않는 교재입니다."));
        mistakeNote.addMistakeNumbers(
                numberScopeCheckService, workbook, saveMistakeNoteInfoDto.getMistakeNumbers());
    }
}
