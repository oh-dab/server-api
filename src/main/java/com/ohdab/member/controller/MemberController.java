package com.ohdab.member.controller;

import com.ohdab.member.controller.mapper.MemberWebMapper;
import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.GetTeacherListRes;
import com.ohdab.member.controller.response.JoinRes;
import com.ohdab.member.controller.response.LoginRes;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.dto.MemberDtoForLogin;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import com.ohdab.member.service.usecase.JoinUsecase;
import com.ohdab.member.service.usecase.LoginUsecase;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JoinUsecase joinUsecase;
    private final LoginUsecase loginUsecase;
    private final GetTeacherListUsecase getTeacherListUsecase;

    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(@Valid @RequestBody JoinReq joinReq) {
        joinUsecase.join(MemberWebMapper.toJoinReqDto(joinReq));
        return ResponseEntity.ok(MemberWebMapper.toJoinRes());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginReq loginReq) {
        MemberDtoForLogin.Response loginResDto =
                loginUsecase.login(MemberWebMapper.toLoginReqDto(loginReq));
        return ResponseEntity.ok(MemberWebMapper.toLoginRes(loginResDto));
    }

    @GetMapping("/test")
    public String adminAndTokenTest() {
        return "hohoho";
    }

    @GetMapping("/teachers")
    public ResponseEntity<GetTeacherListRes> getTeacherList() {
        List<MemberDtoForGetTeacherList.Response> teacherDtoList =
                getTeacherListUsecase.getTeacherList();
        return ResponseEntity.ok(MemberWebMapper.teacherDtoListToResponseList(teacherDtoList));
    }
}
