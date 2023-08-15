package com.ohdab.classroom.controller.request;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddStudentReq {

    @NotNull(message = "학생 이름은 필수 입력 값입니다.")
    private String studentName;
}
