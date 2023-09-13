package com.ohdab.member.event;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherDeletedEvent {

    private long teacherId;
}
