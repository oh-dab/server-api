package com.ohdab.member.repository;

import com.ohdab.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberInfoName(String name);

    @Query("select m from Member m where m.id = :memberId and m.status = 'ACTIVE'")
    Optional<Member> findActiveMemberById(@Param("memberId") long memberId);
}
