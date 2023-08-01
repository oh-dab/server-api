package com.ohdab.member.repository;

import com.ohdab.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberInfoName(String name);

    List<Member> findByAuthorities(String role);

    boolean existsByMemberInfoName(String name);

    Long countByMemberInfoNameContaining(String name);
}
