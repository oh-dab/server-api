package com.ohdab.domain.group;

import com.ohdab.domain.student.Student;
import com.ohdab.domain.workbook.Workbook;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Classroom {

    private ClassroomInfo classroomInfo;
    private List<Workbook> workbooks;
    private List<Student> students;

    @Builder
    public Classroom(ClassroomInfo classroomInfo) {
        if (classroomInfo == null) {
            throw new IllegalStateException("ClassroomInfo cannot be null");
        }
        setClassroomInfo(classroomInfo);
    }

    private void setClassroomInfo(ClassroomInfo classroomInfo) {
        this.classroomInfo = classroomInfo;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalStateException("Student cannot be null.");
        }
        this.students.add(student);
    }

    public void addWorkbook(Workbook workbook) {
        if (workbook == null) {
            throw new IllegalStateException("Workbook cannot be null.");
        }
        this.workbooks.add(workbook);
    }
}
