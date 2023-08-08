package com.ohdab.member.repository;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberInfoName(String name);

    List<Member> findByAuthoritiesContaining(Authority role);

    boolean existsByMemberInfoName(String name);

    Long countByMemberInfoNameContaining(String name);

    Optional<Member> findActiveMemberById(long memberId);
}
