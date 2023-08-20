package com.ohdab.core.util.event.handler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ohdab.member.event.TeacherAddedEvent;
import com.ohdab.member.service.dto.MemberDtoForAddTeacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
public class TeacherAddedHandlerTest {

    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private ApplicationEvents events;

    @Test
    @DisplayName("선생님 추가시 회원가입 성공 테스트")
    void 선생님_추가시_회원가입_성공() {
        // given
        String name = "선생님";
        MemberDtoForAddTeacher.Request addTeacherReq =
                MemberDtoForAddTeacher.Request.builder().name(name).build();

        // when
        publisher.publishEvent(TeacherAddedEvent.builder().name(addTeacherReq.getName()).build());

        // then
        assertThat(events.stream(TeacherAddedEvent.class).count()).isEqualTo(1);
    }
}
