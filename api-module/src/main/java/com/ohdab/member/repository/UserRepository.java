package com.ohdab.member.repository;

import com.ohdab.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberProfile_Name(String name);
}
