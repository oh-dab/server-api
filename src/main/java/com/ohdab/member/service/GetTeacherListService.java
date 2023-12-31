package com.ohdab.member.service;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForGetTeacherList;
import com.ohdab.member.service.usecase.GetTeacherListUsecase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetTeacherListService implements GetTeacherListUsecase {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDtoForGetTeacherList.Response> getTeacherList() {
        Authority role = new Authority("TEACHER");
        List<Member> memberList = memberRepository.findByAuthoritiesContaining(role);
        return memberDomainListToMemberDtoList(memberList);
    }

    private List<MemberDtoForGetTeacherList.Response> memberDomainListToMemberDtoList(
            List<Member> members) {
        return members.stream()
                .map(
                        member ->
                                MemberDtoForGetTeacherList.Response.builder()
                                        .id(member.getId())
                                        .name(member.getMemberInfo().getName())
                                        .authorities(createAuthoritiesDto(member.getAuthorities()))
                                        .status(member.getStatus().name())
                                        .build())
                .collect(Collectors.toList());
    }

    private static List<String> createAuthoritiesDto(List<Authority> authorities) {
        return authorities.stream().map(auth -> auth.getRole()).collect(Collectors.toList());
    }
}
