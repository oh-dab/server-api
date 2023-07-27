package com.ohdab.mistakenote.service;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.mistakenote.exception.NoWorkbookException;
import com.ohdab.mistakenote.repository.MistakeNoteRepository;
import com.ohdab.mistakenote.service.dto.MistakeNoteInfoDto;
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

    private final MistakeNoteRepository mistakeNoteRepository;

    @Override
    public List<MistakeNoteInfoDto> getMistakeNoteInfoByStudent(long workbookId, long studentId) {
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
                (number, count) -> {
                    mistakeNoteInfo.add(
                            MistakeNoteInfoDto.builder()
                                    .wrongNumber(number)
                                    .wrongCount(count)
                                    .build());
                });
        return mistakeNoteInfo;
    }
}
