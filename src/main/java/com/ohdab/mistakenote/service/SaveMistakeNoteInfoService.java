package com.ohdab.mistakenote.service;

import static com.ohdab.mistakenote.service.helper.MistakeNoteHelperService.isNotExistingMember;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.exception.NoMistakeNoteException;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.SaveMistakeNoteInfoDto;
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

    private final NumberScopeCheckService numberScopeCheckService;
    private final MemberRepository memberRepository;
    private final MistakeNoteRepository mistakeNoteRepository;
    private final WorkbookRepository workbookRepository;

    @Override
    public void saveMistakeNoteInfo(SaveMistakeNoteInfoDto saveMistakeNoteInfoDto) {
        if (isNotExistingMember(memberRepository, saveMistakeNoteInfoDto.getStudentId())) {
            throw new NoMemberException(ExceptionEnum.NO_MEMBER.getMessage());
        }
        MistakeNote mistakeNote =
                mistakeNoteRepository
                        .findByWorkbookIdAndStudentId(
                                new WorkbookId(saveMistakeNoteInfoDto.getWorkbookId()),
                                new StudentId(saveMistakeNoteInfoDto.getStudentId()))
                        .orElseThrow(
                                () ->
                                        new NoMistakeNoteException(
                                                ExceptionEnum.NO_MISTAKE_NOTE.getMessage()));
        Workbook workbook =
                workbookRepository
                        .findById(saveMistakeNoteInfoDto.getWorkbookId())
                        .orElseThrow(
                                () ->
                                        new NoWorkbookException(
                                                ExceptionEnum.NO_WORKBOOK.getMessage()));
        mistakeNote.addMistakeNumbers(
                numberScopeCheckService, workbook, saveMistakeNoteInfoDto.getMistakeNumbers());
    }
}
