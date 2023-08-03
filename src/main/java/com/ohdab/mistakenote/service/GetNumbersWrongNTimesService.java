package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.exception.NoNumbersWrongNTimesException;
import com.ohdab.mistakenote.repository.mapper.MistakeRecordMapper;
import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetNumbersWrongNTimesService implements GetNumbersWrongNTimesUsecase {

    private final MistakeRecordMapper mistakeRecordMapper;

    @Override
    public GetNumbersWrongNTimesDto.Response getNumbersWrongNTimes(
            GetNumbersWrongNTimesDto.Request getNumbersWrongNTimeDto) {
        List<Integer> numbersWrongNTimes =
                mistakeRecordMapper.findNumbersWrongNTimes(getNumbersWrongNTimeDto);
        return GetNumbersWrongNTimesDto.Response.builder()
                .wrongNumbers(wrongNumbersToString(numbersWrongNTimes))
                .build();
    }

    private String wrongNumbersToString(List<Integer> numbersWrongNTimes) {
        if (numbersWrongNTimes.isEmpty()) {
            throw new NoNumbersWrongNTimesException("틀린 문제가 없습니다.");
        }
        return numbersWrongNTimes.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
