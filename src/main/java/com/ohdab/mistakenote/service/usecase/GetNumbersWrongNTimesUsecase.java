package com.ohdab.mistakenote.service.usecase;

import com.ohdab.mistakenote.service.dto.GetNumbersWrongNTimesDto;

public interface GetNumbersWrongNTimesUsecase {

    GetNumbersWrongNTimesDto.Response getNumbersWrongNTimes(
            GetNumbersWrongNTimesDto.Request getNumbersWrongNTimeDto);
}
