package com.ohdab.entity.main.java.com.ohdab.entity;

import lombok.Builder;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

//@Getter
//@Entity
//@Table(name = "TEACHER")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherJpa extends BaseEntity {

//    @Id
//    @GeneratedValue
//    @Column(name = "teacher_id")
//    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberJpa memberJpa;

    @OneToMany(mappedBy = "teacherJpa", fetch = FetchType.LAZY)
    private List<ClassroomJpa> classroomJpas;

    @Builder
    public TeacherJpa(MemberJpa memberJpa, List<ClassroomJpa> classroomJpas) {
        this.memberJpa = memberJpa;
        this.classroomJpas = classroomJpas;
    }
}
