package com.ohdab.member.domain.teacher.teacherid;

import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherId {

    private long id;

    private void setId(long id) {
        this.id = id;
    }
}
