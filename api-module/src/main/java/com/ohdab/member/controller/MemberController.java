package com.ohdab.member.controller;

import com.ohdab.member.application.service.dto.LoginResDto;
import com.ohdab.member.application.service.spi.JoinService;
import com.ohdab.member.application.service.spi.LoginService;
import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.JoinRes;
import com.ohdab.member.controller.response.LoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final JoinService joinService;
    private final LoginService loginService;

    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(@RequestBody JoinReq joinReq) {
        joinService.join(JoinReq.toDto(joinReq));
        return ResponseEntity.ok(JoinRes.builder().message("회원가입이 완료되었습니다.").build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        LoginResDto loginResDto = loginService.login(LoginReq.toDto(loginReq));
        return ResponseEntity.ok(LoginRes.toRes(loginResDto));
    }

    @GetMapping("/test")
    public String adminAndTokenTest() {
        return "hohoho";
    }
}
