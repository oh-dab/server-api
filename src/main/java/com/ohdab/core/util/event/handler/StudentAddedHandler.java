package com.ohdab.core.util.event.handler;

import com.ohdab.classroom.event.StudentAddedEvent;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.usecase.JoinUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentAddedHandler {

    private final JoinUsecase joinUsecase;

    @EventListener(StudentAddedEvent.class)
    public void join(StudentAddedEvent event) {
        joinUsecase.join(
                MemberDtoForJoin.Request.builder()
                        .name(event.getStudentName())
                        .password(event.getPassword())
                        .role(List.of("STUDENT"))
                        .build());
    }
}
