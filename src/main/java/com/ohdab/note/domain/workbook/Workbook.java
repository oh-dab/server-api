package com.ohdab.note.domain.workbook;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Workbook {

    private String name;
    private String description;
    private int startingNumber;
    private int endingNumber;
}
