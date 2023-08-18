package com.ohdab.core.util.event.handler;

import com.ohdab.member.domain.Member;
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
        member.withdrawal();
        memberRepository.save(member);
    }
}
