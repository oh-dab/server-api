package com.ohdab.core.util.event.handler;

import com.ohdab.member.event.TeacherAddedEvent;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.usecase.JoinUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherAddedHandler {

    private final JoinUsecase joinUsecase;

    @EventListener(TeacherAddedEvent.class)
    public void join(TeacherAddedEvent event) {
        joinUsecase.join(
                MemberDtoForJoin.Request.builder()
                        .name(event.getName())
                        .password(event.getPassword())
                        .role(List.of("TEACHER"))
                        .build());
    }
}
