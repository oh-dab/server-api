package com.ohdab.member.controller;

import com.ohdab.member.controller.mapper.MemberWebMapper;
import com.ohdab.member.controller.request.AddTeacherReq;
import com.ohdab.member.controller.request.JoinReq;
import com.ohdab.member.controller.request.LoginReq;
import com.ohdab.member.controller.response.*;
import com.ohdab.member.service.dto.MemberDtoForAddTeacher;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.dto.MemberDtoForLogin;
import com.ohdab.member.service.usecase.*;
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
    private final AddTeacherUsecase addTeacherUsecase;
    private final DeleteTeacherUsecase deleteTeacherUsecase;
    private final WithdrawlUsecase withdrawlUsecase;

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

    @PatchMapping("/withdrawl/{member-id}")
    public ResponseEntity<WithdrawlRes> withdrawl(@PathVariable(name = "member-id") long memberId) {
        withdrawlUsecase.withdrawl(memberId);
        return ResponseEntity.ok(MemberWebMapper.createWithdrawlRes());
    }

    @GetMapping("/teachers")
    public ResponseEntity<GetTeacherListRes> getTeacherList() {
        List<MemberDtoForGetTeacherList.Response> teacherDtoList =
                getTeacherListUsecase.getTeacherList();
        return ResponseEntity.ok(MemberWebMapper.teacherDtoListToResponseList(teacherDtoList));
    }

    @PostMapping("/teachers/enrollment")
    public ResponseEntity<AddTeacherRes> addTeacher(
            @RequestBody @Valid AddTeacherReq addTeacherReq) {
        MemberDtoForAddTeacher.Request addTeacherDto =
                MemberWebMapper.addTeacherRequestToDto(addTeacherReq);
        addTeacherUsecase.addTeacher(addTeacherDto);
        return ResponseEntity.ok(MemberWebMapper.createAddTeacherRes());
    }

    @PatchMapping("/teachers/expulsion/{teacher-id}")
    public ResponseEntity<DeleteTeacherRes> deleteTeacher(@PathVariable("teacher-id") long id) {
        deleteTeacherUsecase.deleteTeacherById(id);
        return ResponseEntity.ok(MemberWebMapper.createDeleteTeacherRes());
    }
}
