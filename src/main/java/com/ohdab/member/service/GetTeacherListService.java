package com.ohdab.member.service;

import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDto;
import com.ohdab.member.service.dto.TeacherReqDto;
import com.ohdab.member.service.mapper.ServiceMemberMapper;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetTeacherListService implements GetTeacherListUsecase {

    private final MemberRepository memberRepository;

    private final ServiceMemberMapper serviceMemberMapper;

    @Override
    public List<MemberDto> getTeacherList() {
        List<Member> memberList = memberRepository.findByAuthorities("TEACHER");
        List<MemberDto> memberDtoList = new ArrayList<>();
        memberList.forEach(m -> memberDtoList.add(serviceMemberMapper.memberDomainToMemberDto(m)));
        return memberDtoList;
    }

    @Override
    public void addTeacher(TeacherReqDto teacherReqDto) {}
}
