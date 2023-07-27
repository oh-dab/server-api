package com.ohdab.mistakenote.service;

import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.helper.MistakeNoteHelperService;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMistakeNoteInfoService implements GetMistakeNoteInfoUsecase {

    private final MistakeNoteRepository mistakeNoteRepository;
    private final MistakeNoteHelperService mistakeNoteHelperService;
    private final MemberRepository memberRepository;
    private final WorkbookRepository workbookRepository;
    @Override
    public List<MistakeNoteInfoDto> getMistakeNoteInfoByStudent(long workbookId, long studentId) {



        return null;
    }
}
