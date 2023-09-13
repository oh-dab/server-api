package com.ohdab.core.util.event.handler;

import static org.assertj.core.api.Assertions.assertThat;

import com.ohdab.classroom.event.StudentAddedEvent;
import com.ohdab.classroom.service.dto.AddStudentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
class StudentAddedHandlerTest {

    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private ApplicationEvents events;

    @DisplayName("학생 추가 이벤트 발생시 회원가입을 시킨다.")
    @Test
    void 학생_추가_이벤트_발생시_회원가입() {
        // given
        final AddStudentDto.Request addStudentReq =
                AddStudentDto.Request.builder().classroomId(1L).studentName("갑").build();

        // when
        publisher.publishEvent(
                StudentAddedEvent.builder().studentName(addStudentReq.getStudentName()).build());

        // then
        long count = events.stream(StudentAddedEvent.class).count();
        assertThat(count).isEqualTo(1);
    }
}
