package com.ohdab.classroom.controller.request;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherIdReq {

    @NotNull(message = "선생님 아이디를 입력해야 반 목록을 얻을 수 있습니다.")
    private long teacherId;
}
