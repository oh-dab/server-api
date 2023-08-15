package com.ohdab.classroom.domain;

import com.ohdab.classroom.domain.classroomInfo.ClassroomInfo;
import com.ohdab.classroom.exception.NoStudentException;
import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.core.exception.ExceptionEnum;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.member.domain.teacher.teacherid.TeacherId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import io.jsonwebtoken.lang.Assert;
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
        Assert.notNull(classroomInfo, ExceptionEnum.IS_NULL.getMessage());
        Assert.notNull(teacher, ExceptionEnum.IS_NULL.getMessage());
        this.classroomInfo = classroomInfo;
        this.teacher = teacher;
    }

    public void addStudent(StudentId student) {
        Assert.notNull(student, ExceptionEnum.IS_NULL.getMessage());
        this.students.add(student);
    }

    public void addWorkbook(WorkbookId workbook) {
        Assert.notNull(workbook, ExceptionEnum.IS_NULL.getMessage());
        this.workbooks.add(workbook);
    }

    public void deleteStudent(long studentId) {
        if (!students.removeIf(student -> student.getId() == studentId)) {
            throw new NoStudentException(ExceptionEnum.NO_STUDENT.getMessage());
        }
    }

    public void updateClassroomInfo(ClassroomInfo classroomInfo) {
        Assert.notNull(classroomInfo, ExceptionEnum.IS_NULL.getMessage());
        this.classroomInfo = classroomInfo;
    }
}
