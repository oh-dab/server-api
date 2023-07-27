package com.ohdab.workbook.domain.workbookid;

import javax.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookId {

    private long id;

    private void setId(long id) {
        this.id = id;
    }
}
