package com.ohdab.core.util.event.handler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ohdab.member.domain.Authority;
import com.ohdab.member.domain.Member;
import com.ohdab.member.domain.memberinfo.MemberInfo;
import com.ohdab.member.event.TeacherDeletedEvent;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
public class TeacherDeletedHandlerTest {

    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private ApplicationEvents events;

    @Test
    @DisplayName("선생님 삭제시 탈퇴 성공 테스트")
    void 선생님_삭제시_탈퇴_성공() {
        // given
        Member member =
                Member.builder()
                        .memberInfo(MemberInfo.builder().name("선생님").password("1234").build())
                        .authorities(List.of(new Authority("TEACHER")))
                        .build();

        // when
        publisher.publishEvent(TeacherDeletedEvent.builder().member(member).build());

        // then
        assertThat(events.stream(TeacherDeletedEvent.class).count()).isEqualTo(1);
        assertThat(member.getStatus().name()).isEqualTo("INACTIVE");
    }
}
