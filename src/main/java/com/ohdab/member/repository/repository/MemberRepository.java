package com.ohdab.member.repository.repository;

import com.ohdab.entity.main.java.com.ohdab.entity.MemberJpa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpa, Long> {

    Optional<MemberJpa> findByName(String name);
}
