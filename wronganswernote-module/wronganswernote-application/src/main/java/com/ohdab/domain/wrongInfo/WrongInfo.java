package com.ohdab.domain.wrongInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WrongInfo {

    private int problemNumber;
    private int wrongCount;
}
