package com.ohdab.classroom.domain;

import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLASSROOM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Classroom extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "classroom_id", nullable = false)
    private Long id;

    @Embedded private ClassroomInfo classroomInfo;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "teacher_id", nullable = false))
    private TeacherId teacher;

    @ElementCollection
    @CollectionTable(name = "STUDENT_LIST", joinColumns = @JoinColumn(name = "classroom_id"))
    private List<StudentId> students = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "WORKBOOK_LIST", joinColumns = @JoinColumn(name = "classroom_id"))
    private List<WorkbookId> workbooks = new ArrayList<>();

    @Builder
    public Classroom(ClassroomInfo classroomInfo, TeacherId teacher) {
        if (classroomInfo == null) {
            throw new IllegalStateException("ClassroomInfo cannot be null");
        }
        if (teacher == null) {
            throw new IllegalStateException("Teacher cannot be null");
        }
        setClassroomInfo(classroomInfo);
        setTeacher(teacher);
    }

    public void setClassroomInfo(ClassroomInfo classroomInfo) {
        this.classroomInfo = classroomInfo;
    }

    private void setTeacher(TeacherId teacher) {
        this.teacher = teacher;
    }

    public void addStudent(StudentId student) {
        if (student == null) {
            throw new IllegalStateException("Student cannot be null.");
        }
        this.students.add(student);
    }

    public void addWorkbook(WorkbookId workbook) {
        if (workbook == null) {
            throw new IllegalStateException("Workbook cannot be null.");
        }
        this.workbooks.add(workbook);
    }
}
