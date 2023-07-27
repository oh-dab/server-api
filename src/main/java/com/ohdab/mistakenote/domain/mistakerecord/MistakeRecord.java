package com.ohdab.mistakenote.domain.mistakerecord;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MistakeRecord {

    @Column(name = "mistake_number", nullable = false)
    private int number;

    @Column(name = "mistake_count", nullable = false)
    private int count;
}
