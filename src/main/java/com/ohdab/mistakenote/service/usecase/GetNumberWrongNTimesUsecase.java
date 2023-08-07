package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetNumberWrongNTimesDto;

public interface GetNumberWrongNTimesUsecase {

    GetNumberWrongNTimesDto.Response getNumberWrongNTimes(
            GetNumberWrongNTimesDto.Request getNumbersWrongNTimeDto);
}
