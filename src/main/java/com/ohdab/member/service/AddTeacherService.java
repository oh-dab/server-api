package com.ohdab.member.service;

import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForAddTeacher;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import com.ohdab.member.service.usecase.JoinUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddTeacherService implements AddTeacherUsecase {

    private final MemberRepository memberRepository;
    private final JoinUsecase joinUsecase;
    private final MemberHelperService memberHelperService;

    @Override
    public void addTeacher(MemberDtoForAddTeacher.Request memberDtoForAddTeacherDto) {
        String name = memberDtoForAddTeacherDto.getName();
        name = changeNameIfDuplicated(name);
        String password = name;
        MemberDtoForJoin.Request memberDtoForJoin =
                MemberDtoForJoin.Request.builder()
                        .name(name)
                        .password(password)
                        .role(List.of("TEACHER", "STUDENT"))
                        .build();
        // TODO:선생님 추가 시 회원가입 요청
        joinUsecase.join(memberDtoForJoin);
        throwIfJoinFailed(name);
    }

    private String changeNameIfDuplicated(String name) {
        if (memberHelperService.checkIfMemberExistByName(memberRepository, name)) {
            long sameNameCount = memberRepository.countByMemberInfoNameContaining(name);
            return name = name + sameNameCount;
        }
        return name;
    }

    private void throwIfJoinFailed(String name) {
        if (!memberHelperService.checkIfMemberExistByName(memberRepository, name)) {
            throw new NoMemberException("Join Failed with teacher name \"" + name + "\"");
        }
    }
}
