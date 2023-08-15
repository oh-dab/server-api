package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;

public interface GetNumbersWrongNTimesUsecase {

    GetNumberWrongNTimesDto.Response getNumberWrongNTimes(
            GetNumberWrongNTimesDto.Request getNumbersWrongNTimeDto);
}
