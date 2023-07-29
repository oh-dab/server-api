package com.ohdab.mistakenote.controller.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMistakeNoteInfoReq {

    private int[] mistakeNumbers;
}
