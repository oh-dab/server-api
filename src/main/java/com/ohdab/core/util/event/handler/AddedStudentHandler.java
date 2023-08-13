package com.ohdab.core.util.event.handler;

import com.ohdab.classroom.event.AddedStudentEvent;
import com.ohdab.member.service.dto.MemberDtoForJoin;
import com.ohdab.member.service.usecase.JoinUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddedStudentHandler {

    private final JoinUsecase joinUsecase;

    @EventListener(AddedStudentEvent.class)
    public void join(AddedStudentEvent event) {
        joinUsecase.join(MemberDtoForJoin.Request.builder()
                .name(event.getStudentName())
                .password(event.getPassword())
                .role(List.of("STUDENT"))
                .build());
    }
}
