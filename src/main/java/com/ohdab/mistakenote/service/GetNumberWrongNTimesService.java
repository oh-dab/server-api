package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.exception.NumberIsOutOfRangeException;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumberWrongNTimesUsecase;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.exception.NoWorkbookException;
import com.ohdab.workbook.repository.WorkbookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetNumberWrongNTimesService implements GetNumberWrongNTimesUsecase {

    private final MistakeRecordMapper mistakeRecordMapper;
    private final WorkbookRepository workbookRepository;

    @Override
    public GetNumberWrongNTimesDto.Response getNumberWrongNTimes(
            GetNumberWrongNTimesDto.Request getNumberWrongNTimeDto) {
        Workbook workbook =
                workbookRepository
                        .findById(getNumberWrongNTimeDto.getWorkbookId())
                        .orElseThrow(() -> new NoWorkbookException("존재하지 않는 교재입니다."));
        checkNumberIsInRange(getNumberWrongNTimeDto, workbook);
        List<Integer> numberWrongNTimes =
                mistakeRecordMapper.findNumbersWrongNTimes(getNumberWrongNTimeDto);
        return GetNumberWrongNTimesDto.Response.builder()
                .wrongNumber(wrongNumbersToString(numberWrongNTimes))
                .build();
    }

    private void checkNumberIsInRange(
            GetNumberWrongNTimesDto.Request getNumberWrongNTimeDto, Workbook workbook) {
        int startingNumber = getStartingNumber(workbook);
        int endingNumber = getEndingNumber(workbook);
        int from = getFrom(getNumberWrongNTimeDto);
        int to = getTo(getNumberWrongNTimeDto);
        if (!fromIsInRange(from, startingNumber, endingNumber)
                || !toIsInRange(to, startingNumber, endingNumber)) {
            throw new NumberIsOutOfRangeException("교재에 존재하지 않는 번호를 요청했습니다.");
        }
    }

    private int getStartingNumber(Workbook workbook) {
        return workbook.getWorkbookInfo().getStartingNumber();
    }

    private int getEndingNumber(Workbook workbook) {
        return workbook.getWorkbookInfo().getEndingNumber();
    }

    private int getTo(GetNumberWrongNTimesDto.Request getNumberWrongNTimeDto) {
        return getNumberWrongNTimeDto.getTo();
    }

    private int getFrom(GetNumberWrongNTimesDto.Request getNumberWrongNTimeDto) {
        return getNumberWrongNTimeDto.getFrom();
    }

    private boolean fromIsInRange(int from, int startingNumber, int endingNumber) {
        return (from < startingNumber || endingNumber < from);
    }

    private boolean toIsInRange(int to, int startingNumber, int endingNumber) {
        return (to < startingNumber || endingNumber < to);
    }

    private String wrongNumbersToString(List<Integer> numberWrongNTimes) {
        if (numberWrongNTimes.isEmpty()) {
            throw new NoNumbersWrongNTimesException("틀린 문제가 없습니다.");
        }
        return numberWrongNTimes.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
