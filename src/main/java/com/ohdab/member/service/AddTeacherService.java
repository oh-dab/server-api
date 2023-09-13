package com.ohdab.member.service;

import com.ohdab.member.event.TeacherAddedEvent;
import com.ohdab.member.service.dto.MemberDtoForAddTeacher;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddTeacherService implements AddTeacherUsecase {

    private final ApplicationEventPublisher publisher;

    @Override
    public void addTeacher(MemberDtoForAddTeacher.Request addTeacherReqDto) {
        String name = addTeacherReqDto.getName();
        publishTeacherAddedEvent(name);
    }

    private void publishTeacherAddedEvent(String name) {
        publisher.publishEvent(TeacherAddedEvent.builder().name(name).build());
    }
}
