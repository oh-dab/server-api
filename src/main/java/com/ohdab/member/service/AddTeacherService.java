package com.ohdab.member.service;

import static com.ohdab.member.service.helper.MemberHelperService.checkIfMemberExistByName;
import static com.ohdab.member.service.helper.MemberHelperService.countMemberNumberContainingName;

import com.ohdab.member.event.TeacherAddedEvent;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.dto.MemberDtoForAddTeacher;
import com.ohdab.member.service.usecase.AddTeacherUsecase;
import com.ohdab.member.service.usecase.JoinUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddTeacherService implements AddTeacherUsecase {

    private final ApplicationEventPublisher publisher;
    private final MemberRepository memberRepository;
    private final JoinUsecase joinUsecase;

    @Override
    public void addTeacher(MemberDtoForAddTeacher.Request addTeacherReqDto) {
        String name = addTeacherReqDto.getName();
        name = changeNameIfDuplicated(name);
        publishTeacherAddedEvent(name);
    }

    private String changeNameIfDuplicated(String name) {
        if (checkIfMemberExistByName(memberRepository, name)) {
            long sameNameCount = countMemberNumberContainingName(memberRepository, name);
            return name = name + sameNameCount;
        }
        return name;
    }

    private void publishTeacherAddedEvent(String name) {
        publisher.publishEvent(TeacherAddedEvent.builder().name(name).build());
    }
}
