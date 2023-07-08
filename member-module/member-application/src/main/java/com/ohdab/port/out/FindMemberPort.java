package com.ohdab.port.out;

import com.ohdab.domain.Member;
import java.util.Optional;

public interface FindMemberPort {
    Optional<Member> findByMemberProfile_Name(String name);
}
