package com.ohdab.classroom.controller.request;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateClassroomReq {

    @NotNull(message = "반 이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "반 설명은 필수 입력값 입니다.")
    private String description;

    private String grade;
}
