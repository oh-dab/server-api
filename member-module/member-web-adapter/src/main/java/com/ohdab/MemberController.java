package com.ohdab;

import com.ohdab.dto.LoginResDto;
import com.ohdab.mapper.MemberWebMapper;
import com.ohdab.port.in.JoinUsecase;
import com.ohdab.port.in.LoginUsecase;
import com.ohdab.request.JoinReq;
import com.ohdab.request.LoginReq;
import com.ohdab.response.JoinRes;
import com.ohdab.response.LoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JoinUsecase joinUsecase;
    private final LoginUsecase loginUsecase;

    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(@RequestBody JoinReq joinReq) {
        joinUsecase.join(MemberWebMapper.toJoinReqDto(joinReq));
        return ResponseEntity.ok(MemberWebMapper.toJoinRes());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        LoginResDto loginResDto = loginUsecase.login(MemberWebMapper.toLoginReqDto(loginReq));
        return ResponseEntity.ok(MemberWebMapper.toLoginRes(loginResDto));
    }

    @GetMapping("/test")
    public String adminAndTokenTest() {
        return "hohoho";
    }
}
