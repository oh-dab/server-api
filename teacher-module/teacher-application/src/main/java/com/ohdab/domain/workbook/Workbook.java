package com.ohdab.domain.workbook;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Workbook {

    private String name;
    private String description;
    private int startingNumber;
    private int endingNumber;

    public Workbook(String name, String description, int startingNumber, int endingNumber) {
        setName(name);
        setDescription(description);
        setRange(startingNumber, endingNumber);
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new IllegalStateException(
                    "Name length cannot exceed 20 : current length \"" + name.length() + "\"");
        }
        this.name = name;
    }

    private void setDescription(String description) {
        if (description != null && description.length() > 30) {
            throw new IllegalStateException(
                    "Description length cannot exceed 30 : current length \""
                            + description.length()
                            + "\"");
        }
        this.description = description;
    }

    private void setRange(int startingNumber, int endingNumber) {
        if (startingNumber < 0
                || startingNumber > 5000
                || endingNumber < 0
                || endingNumber > 5000) {
            throw new IllegalStateException("");
        }
        if (startingNumber > endingNumber) {
            throw new IllegalStateException("");
        }
        this.startingNumber = startingNumber;
        this.endingNumber = endingNumber;
    }
}
