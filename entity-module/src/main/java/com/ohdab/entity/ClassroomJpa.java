package com.ohdab.entity;

import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "CLASSROOM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassroomJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "classroom_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private TeacherJpa teacherJpa;

    @OneToMany(mappedBy = "classroomJpa", fetch = FetchType.LAZY)
    private List<StudentJpa> studentJpas;

    @OneToMany(mappedBy = "classroomJpa", fetch = FetchType.LAZY)
    private List<WorkbookJpa> workbookJpas;

    @Builder
    public ClassroomJpa(
            String name,
            String description,
            Grade grade,
            TeacherJpa teacherJpa,
            List<StudentJpa> studentJpas,
            List<WorkbookJpa> workbookJpas) {
        this.name = name;
        this.description = description;
        this.grade = grade;
        this.teacherJpa = teacherJpa;
        this.studentJpas = studentJpas;
        this.workbookJpas = workbookJpas;
    }
}
