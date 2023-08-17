package com.ohdab.core.util.event.handler;

import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.MemberStatus;
import com.ohdab.member.event.TeacherDeletedEvent;
import com.ohdab.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherDeletedHandler {

    private final MemberRepository memberRepository;

    @EventListener(TeacherDeletedEvent.class)
    public void withdrawal(TeacherDeletedEvent event) {
        Member member = event.getMember();
        throwIfInactiveMember(member);
        member.withdrawal();
        memberRepository.save(member);
    }

    private void throwIfInactiveMember(Member member) {
        if (member.getStatus() == MemberStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Already withdrawal Member with id \"" + member.getId() + "\"");
        }
    }
}
