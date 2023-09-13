package com.ohdab.mistakenote.service;

import com.ohdab.core.exception.ExceptionEnum;
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
                        .orElseThrow(
                                () ->
                                        new NoWorkbookException(
                                                ExceptionEnum.NO_WORKBOOK.getMessage()));
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
        if (isNotInRange(from, startingNumber, endingNumber)
                || isNotInRange(to, startingNumber, endingNumber)) {
            throw new NumberIsOutOfRangeException(
                    ExceptionEnum.NUMBER_IS_OUT_OF_RANGE.getMessage());
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

    private boolean isNotInRange(int target, int startingNumber, int endingNumber) {
        return (target < startingNumber || endingNumber < target);
    }

    private String wrongNumbersToString(List<Integer> numberWrongNTimes) {
        if (numberWrongNTimes.isEmpty()) {
            throw new NoNumbersWrongNTimesException(
                    ExceptionEnum.NO_NUMBERS_WRONG_N_TIMES.getMessage());
        }
        return numberWrongNTimes.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
