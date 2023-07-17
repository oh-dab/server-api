package com.ohdab.domain.group;

import com.ohdab.domain.student.Student;
import com.ohdab.domain.workbook.Workbook;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Classroom {

    private ClassroomInfo classroomInfo;
    private List<Workbook> workbooks;
    private List<Student> students;
}