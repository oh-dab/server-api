package com.ohdab.mistakenote.service;

import static com.ohdab.mistakenote.service.helper.MistakeNoteHelperService.isNotExistingMember;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.repository.mapper.MemberMapper;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.exception.NoMistakeNoteException;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.AllMistakeNoteInfoDto;
import com.ohdab.mistakenote.service.dto.GetAllMistakeNoteInfoDto.Response.StudentInfoDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto;
import com.ohdab.mistakenote.service.dto.GetMistakeNoteInfoOfStudentDto.Response.MistakeNoteInfoDto;
import com.ohdab.mistakenote.service.usecase.GetMistakeNoteInfoUsecase;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMistakeNoteInfoService implements GetMistakeNoteInfoUsecase {

    private final MistakeNoteRepository mistakeNoteRepository;
    private final MemberRepository memberRepository;
    private final MistakeRecordMapper mistakeRecordMapper;
    private final MemberMapper memberMapper;

    @Override
    public GetMistakeNoteInfoOfStudentDto.Response getMistakeNoteInfoOfStudent(
            long workbookId, long studentId) {
        if (isNotExistingMember(memberRepository, studentId)) {
            throw new NoMemberException("존재하지 않는 회원입니다.");
        }
        MistakeNote mistakeNote =
                mistakeNoteRepository
                        .findByWorkbookIdAndStudentId(
                                new WorkbookId(workbookId), new StudentId(studentId))
                        .orElseThrow(() -> new NoMistakeNoteException("존재하지 않는 오답노트입니다."));
        return mapToMistakeNoteInfo(mistakeNote);
    }

    private GetMistakeNoteInfoOfStudentDto.Response mapToMistakeNoteInfo(MistakeNote mistakeNote) {
        List<MistakeNoteInfoDto> mistakeNoteInfo = new ArrayList<>();
        Map<Integer, Integer> mistakeRecords = mistakeNote.getMistakeRecords();
        mistakeRecords.forEach(
                (number, count) ->
                        mistakeNoteInfo.add(
                                MistakeNoteInfoDto.builder()
                                        .wrongNumber(number)
                                        .wrongCount(count)
                                        .build()));
        return GetMistakeNoteInfoOfStudentDto.Response.builder()
                .mistakeNoteInfo(mistakeNoteInfo)
                .build();
    }

    @Override
    public GetAllMistakeNoteInfoDto.Response getAllMistakeNoteInfo(long workbookId) {
        List<MistakeNote> mistakeNotes =
                mistakeNoteRepository.findByWorkbookId(new WorkbookId(workbookId));
        List<StudentInfoDto> students = memberMapper.findAllStudent(getStudentIdList(mistakeNotes));
        List<AllMistakeNoteInfoDto> allMistakeNoteInfoDto =
                mistakeRecordMapper.findAllMistakeNoteInfo(getMistakeNoteIdList(mistakeNotes));
        return GetAllMistakeNoteInfoDto.Response.builder()
                .students(students)
                .allMistakeNoteInfo(allMistakeNoteInfoDto)
                .build();
    }

    private List<Long> getStudentIdList(List<MistakeNote> mistakeNotes) {
        return mistakeNotes.stream()
                .map(mistakeNote -> mistakeNote.getStudentId().getId())
                .collect(Collectors.toList());
    }

    private List<Long> getMistakeNoteIdList(List<MistakeNote> mistakeNotes) {
        return mistakeNotes.stream().map(MistakeNote::getId).collect(Collectors.toList());
    }
}
