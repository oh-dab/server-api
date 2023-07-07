package com.ohdab.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinReq {

    @NotNull(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotNull(message = "권한정보는 필수 입력 값입니다.")
    private List<String> role;
}
