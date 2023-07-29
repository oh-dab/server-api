package com.ohdab.mistakenote.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveMistakeNoteInfoReq {

    int[] mistakeNumbers;
}
