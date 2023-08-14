package com.ohdab.workbook.domain.workbookInfo;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.workbook.exception.ContentOverflowException;
import com.ohdab.workbook.exception.InvalidWorkbookNumberRangeException;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookInfo {

    private String name;
    private String description;
    private int startingNumber;
    private int endingNumber;

    @Builder
    public WorkbookInfo(String name, String description, int startingNumber, int endingNumber) {
        setName(name);
        setDescription(description);
        setRange(startingNumber, endingNumber);
    }

    private void setName(String name) {
        if (name.length() > 20) {
            throw new ContentOverflowException(ExceptionEnum.CONTENT_OVERFLOW.getMessage());
        }
        this.name = name;
    }

    private void setDescription(String description) {
        if (description != null && description.length() > 30) {
            throw new ContentOverflowException(ExceptionEnum.CONTENT_OVERFLOW.getMessage());
        }
        this.description = description;
    }

    private void setRange(int startingNumber, int endingNumber) {
        if (startingNumber < 0
                || startingNumber > 5000
                || endingNumber < 0
                || endingNumber > 5000) {
            throw new InvalidWorkbookNumberRangeException(
                    ExceptionEnum.INVALID_WORKBOOK_NUMBER_RANGE.getMessage());
        }
        if (startingNumber > endingNumber) {
            throw new InvalidWorkbookNumberRangeException(
                    ExceptionEnum.INVALID_WORKBOOK_NUMBER_RANGE.getMessage());
        }
        this.startingNumber = startingNumber;
        this.endingNumber = endingNumber;
    }
}
