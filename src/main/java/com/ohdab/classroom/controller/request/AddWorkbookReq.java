package com.ohdab.classroom.controller.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddWorkbookReq {

    @NotNull(message = "교재 이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "교재 설명은 필수 입력값 입니다.")
    private String description;

    @NotNull(message = "교재 문제의 시작 번호는 필수 입력값 입니다.")
    private int startingNumber;

    @NotNull(message = "교재 문제의 끝 번호는 필수 입력값 입니다.")
    private int endingNumber;
}
