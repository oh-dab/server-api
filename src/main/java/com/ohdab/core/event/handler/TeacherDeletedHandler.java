package com.ohdab.core.event.handler;

import com.ohdab.member.event.TeacherDeletedEvent;
import com.ohdab.member.service.usecase.WithdrawlUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeacherDeletedHandler {

    private final WithdrawlUsecase withdrawlUsecase;

    @EventListener(TeacherDeletedEvent.class)
    public void withdrawal(TeacherDeletedEvent event) {
        withdrawlUsecase.withdrawl(event.getTeacherId());
    }
}
