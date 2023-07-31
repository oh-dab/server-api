package com.ohdab.mistakenote.service;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.exception.NoWorkbookException;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.helper.MistakeNoteHelperService;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMistakeNoteInfoService implements GetMistakeNoteInfoUsecase {

    private final MistakeNoteHelperService mistakeNoteHelperService;
    private final MistakeNoteRepository mistakeNoteRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MistakeNoteInfoDto> getMistakeNoteInfoByStudent(long workbookId, long studentId) {
        if (mistakeNoteHelperService.isNotExistingMember(memberRepository, studentId)) {
            throw new NoMemberException("존재하지 않는 회원입니다.");
        }
        MistakeNote mistakeNote =
                mistakeNoteRepository
                        .findByWorkbookIdAndStudentId(
                                new WorkbookId(workbookId), new StudentId(studentId))
                        .orElseThrow(() -> new NoWorkbookException("존재하지 않는 오답노트입니다."));
        return mapToMistakeNoteInfo(mistakeNote);
    }

    private List<MistakeNoteInfoDto> mapToMistakeNoteInfo(MistakeNote mistakeNote) {
        List<MistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        Map<Integer, Integer> mistakeRecords = mistakeNote.getMistakeRecords();
        mistakeRecords.forEach(
                (number, count) ->
                        mistakeNoteInfo.add(
                                MistakeNoteInfoDto.builder()
                                        .wrongNumber(number)
                                        .wrongCount(count)
                                        .build()));
        return mistakeNoteInfo;
    }

    @Override
    public GetAllMistakeNoteInfoDto getAllMistakeNoteInfo(long workbookId) {
        return null;
    }
}
