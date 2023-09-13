package com.ohdab.member.service;

import static com.ohdab.member.service.helper.MemberHelperService.findExistingMemberById;

import com.ohdab.member.domain.Member;
import com.ohdab.member.event.TeacherDeletedEvent;
import com.ohdab.member.repository.MemberRepository;
import com.ohdab.member.service.usecase.DeleteTeacherUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteTeacherService implements DeleteTeacherUsecase {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public void deleteTeacherById(long id) {
        Member teacher = findExistingMemberById(memberRepository, id);
        publishTeacherDeletedEvent(teacher.getId());
    }

    private void publishTeacherDeletedEvent(Long teacherId) {
        publisher.publishEvent(TeacherDeletedEvent.builder().teacherId(teacherId).build());
    }
}
