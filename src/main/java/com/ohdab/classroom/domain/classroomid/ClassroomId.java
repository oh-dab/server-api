package com.ohdab.classroom.domain.classroomid;

import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomId {

    private long id;

    private void setId(long id) {
        this.id = id;
    }
}
