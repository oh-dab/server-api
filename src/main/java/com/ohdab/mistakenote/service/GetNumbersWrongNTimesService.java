package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetNumbersWrongNTimesService implements GetNumbersWrongNTimesUsecase {

    @Override
    public GetNumbersWrongNTimesDto.Response getNumbersWrongNTimes(
            GetNumbersWrongNTimesDto.Request getNumbersWrongNTimeDto) {
        return null;
    }
}
