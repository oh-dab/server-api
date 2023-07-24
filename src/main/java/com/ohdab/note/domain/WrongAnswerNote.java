package com.ohdab.note.domain;

import com.ohdab.note.domain.student.Student;
import com.ohdab.note.domain.workbook.Workbook;
import com.ohdab.note.domain.wrongInfo.WrongInfo;
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
