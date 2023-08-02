package com.ohdab.member.service;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDto;
import com.ohdab.member.service.mapper.ServiceMemberMapper;
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
    public List<MemberDto> getTeacherList() {
        Authority role = new Authority("TEACHER");
        List<Member> memberList = memberRepository.findByAuthoritiesContaining(role);
        return ServiceMemberMapper.memberDomainListToMemberDtoList(memberList);
    }
}
