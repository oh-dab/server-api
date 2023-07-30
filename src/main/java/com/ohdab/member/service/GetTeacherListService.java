package com.ohdab.member.service;

import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.TeacherReqDto;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetTeacherListService implements GetTeacherListUsecase {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getTeacherList() {
        return memberRepository.findByAuthorities("TEACHER");
    }

    @Override
    public void addTeacher(TeacherReqDto teacherReqDto) {}
}
