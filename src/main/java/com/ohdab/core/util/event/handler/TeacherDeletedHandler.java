package com.ohdab.core.util.event.handler;

import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.MemberStatus;
import com.ohdab.member.event.TeacherDeletedEvent;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherDeletedHandler{

    @EventListener(TeacherDeletedEvent.class)
    public void withdrawal(TeacherDeletedEvent event){
        Member member = event.getMember();
        throwIfInactiveMember(member);
        member.withdrawal();
    }

    private void throwIfInactiveMember(Member member) {
        if (member.getStatus() == MemberStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Already withdrawal Member with id \"" + member.getId() + "\"");
        }
    }
}
