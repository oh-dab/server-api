package com.ohdab.mistakenote.service;

import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;
import com.ohdab.mistakenote.service.usecase.GetNumbersWrongNTimesUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetNumbersWrongNTimesService implements GetNumbersWrongNTimesUsecase {

    @Override
    public GetNumberWrongNTimesDto.Response getNumberWrongNTimes(
            GetNumberWrongNTimesDto.Request getNumberWrongNTimeDto) {
        return null;
    }
}
