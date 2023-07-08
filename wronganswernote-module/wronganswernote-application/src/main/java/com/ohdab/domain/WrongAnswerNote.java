package com.ohdab.domain;

import com.ohdab.domain.student.Student;
import com.ohdab.domain.workbook.Workbook;
import com.ohdab.domain.wrongInfo.WrongInfo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WrongAnswerNote {

    private Long id;
    private Workbook workbook;
    private Student student;
    private List<WrongInfo> wrongInfos;
}
