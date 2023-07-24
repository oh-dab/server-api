package com.ohdab.member.service.port.out;

import com.ohdab.member.domain.Member;

import java.util.Optional;

public interface FindMemberPort {
    Optional<Member> findByName(String name);
}
