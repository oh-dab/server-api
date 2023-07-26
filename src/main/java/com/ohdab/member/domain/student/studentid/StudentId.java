package com.ohdab.member.domain.student.studentid;

import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentId {

    private long id;

    private void setId(long id) {
        this.id = id;
    }
}
