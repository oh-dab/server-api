package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.exception.NumberIsOutOfRangeException;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
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
public class GetNumbersWrongNTimesService implements GetNumbersWrongNTimesUsecase {

    private final MistakeRecordMapper mistakeRecordMapper;
    private final WorkbookRepository workbookRepository;

    @Override
    public GetNumbersWrongNTimesDto.Response getNumbersWrongNTimes(
            GetNumbersWrongNTimesDto.Request getNumbersWrongNTimeDto) {
        Workbook workbook =
                workbookRepository
                        .findById(getNumbersWrongNTimeDto.getWorkbookId())
                        .orElseThrow(() -> new NoWorkbookException("존재하지 않는 교재입니다."));
        checkNumberIsInRange(getNumbersWrongNTimeDto, workbook);
        List<Integer> numbersWrongNTimes =
                mistakeRecordMapper.findNumbersWrongNTimes(getNumbersWrongNTimeDto);
        return GetNumbersWrongNTimesDto.Response.builder()
                .wrongNumbers(wrongNumbersToString(numbersWrongNTimes))
                .build();
    }

    private void checkNumberIsInRange(
            GetNumbersWrongNTimesDto.Request getNumbersWrongNTimeDto, Workbook workbook) {
        int startingNumber = workbook.getWorkbookInfo().getStartingNumber();
        int endingNumber = workbook.getWorkbookInfo().getEndingNumber();
        if (getNumbersWrongNTimeDto.getFrom() < startingNumber
                || endingNumber < getNumbersWrongNTimeDto.getFrom()
                || getNumbersWrongNTimeDto.getTo() < startingNumber
                || endingNumber < getNumbersWrongNTimeDto.getTo()) {
            throw new NumberIsOutOfRangeException("교재에 존재하지 않는 번호를 요청했습니다.");
        }
    }

    private String wrongNumbersToString(List<Integer> numbersWrongNTimes) {
        if (numbersWrongNTimes.isEmpty()) {
            throw new NoNumbersWrongNTimesException("틀린 문제가 없습니다.");
        }
        return numbersWrongNTimes.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
