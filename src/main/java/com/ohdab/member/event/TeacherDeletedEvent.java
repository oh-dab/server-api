package com.ohdab.member.event;

import com.ohdab.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherDeletedEvent {

    private Member member;

    @Builder
    public TeacherDeletedEvent(Member member) {
        this.member = member;
    }
}
