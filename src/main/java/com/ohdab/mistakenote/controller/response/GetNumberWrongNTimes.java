package com.ohdab.mistakenote.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetNumberWrongNTimes {

    private String wrongNumber;
}
