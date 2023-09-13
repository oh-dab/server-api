package com.ohdab.member.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddTeacherReq {

    @NotBlank(message = "Teacher name cannot be blank")
    private String teacherName;
}
