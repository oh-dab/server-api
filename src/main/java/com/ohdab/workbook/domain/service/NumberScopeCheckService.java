package com.ohdab.workbook.domain.service;

import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.workbook.domain.Workbook;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NumberScopeCheckService {

    public void numberScopeCheck(Workbook workbook, List<Integer> numbers) {
        int startingNumber = getStartingNumber(workbook);
        int endingNumber = getEndingNumber(workbook);
        verify(numbers, startingNumber, endingNumber);
    }

    private int getEndingNumber(Workbook workbook) {
        return workbook.getWorkbookInfo().getEndingNumber();
    }

    private int getStartingNumber(Workbook workbook) {
        return workbook.getWorkbookInfo().getStartingNumber();
    }

    private void verify(List<Integer> numbers, int startingNumber, int endingNumber) {
        numbers.forEach(
                number -> {
                    if (number < startingNumber || number > endingNumber) {
                        throw new IllegalArgumentException(
                                ExceptionEnum.NO_NUMBERS_IN_BOOK.getMessage());
                    }
                });
    }
}
