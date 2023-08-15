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
public class UpdateWorkbookInfoReq {

    @NotNull(message = "교재 이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "교재 설명은 필수 입력값 입니다.")
    private String description;
}
