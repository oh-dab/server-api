package com.ohdab.port.out;

import com.ohdab.domain.Member;
import java.util.Optional;

public interface FindMemberPort {
    Optional<Member> findByName(String name);
}
