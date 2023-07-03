package com.miracle.user.controller;

import com.miracle.user.controller.request.JoinReq;
import com.miracle.user.controller.response.JoinRes;
import com.miracle.user.service.JoinUserService;
import com.miracle.user.service.dto.JoinReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final JoinUserService joinService;

    @GetMapping("/join")
    public ResponseEntity<JoinRes> join(@RequestBody JoinReq joinReq) {
        JoinReqDto joinReqDto = JoinReq.toDto(joinReq);
        joinService.join(joinReqDto);
        return ResponseEntity.ok(JoinRes.builder().message("회원가입이 완료되었습니다.").build());
    }
}
