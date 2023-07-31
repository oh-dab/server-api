package com.ohdab.member.service;

import com.ohdab.member.exception.NoMemberException;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.JoinReqDto;
import com.ohdab.member.service.dto.TeacherReqDto;
import com.ohdab.member.service.helper.MemberHelperService;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddTeacherService implements AddTeacherUsecase {

    private final MemberRepository memberRepository;
    // private final JoinService joinService;
    private final MemberHelperService memberHelperService;

    @Override
    public void addTeacher(TeacherReqDto teacherReqDto) {
        String name = teacherReqDto.getName();
        name = changeIfDuplicatedName(name);
        String password = name;
        JoinReqDto joinReqDto =
                JoinReqDto.builder()
                        .name(name)
                        .password(password)
                        .role(List.of("TEACHER", "STUDENT"))
                        .build();
        // TODO:선생님 추가 시 회원가입 요청
        // joinService.join(joinReqDto);
        // throwIfJoinFailed(name);
    }

    private String changeIfDuplicatedName(String name) {
        if (memberHelperService.checkIfMemberExistByName(memberRepository, name)) {
            Long sameNameCount = memberRepository.countByNameContaining(name);
            return name = name + sameNameCount.toString();
        }
        return name;
    }

    private void throwIfJoinFailed(String name) {
        if (!memberHelperService.checkIfMemberExistByName(memberRepository, name)) {
            throw new NoMemberException("Join Failed with teacher name \"" + name + "\"");
        }
    }
}
