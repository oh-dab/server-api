package com.ohdab.member.repository;

import com.ohdab.member.domain.Member;
import com.ohdab.member.service.dto.MemberDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<MemberDto> findByMemberInfoName(String name);

    List<MemberDto> findByAuthorities(String role);
}
