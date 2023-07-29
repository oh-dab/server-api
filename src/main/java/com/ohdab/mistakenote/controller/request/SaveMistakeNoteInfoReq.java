package com.ohdab.mistakenote.controller.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMistakeNoteInfoReq {

    @NotNull(message = "기록할 문제 번호를 하나 이상 입력해야 합니다.")
    @Size(min = 1, max = 6000)
    private List<Integer> mistakeNumbers;
}
