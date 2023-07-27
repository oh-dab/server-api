package com.ohdab.core.template;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMessage {

    private int errorCode;
    private String message;
}
