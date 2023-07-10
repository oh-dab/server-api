package com.ohdab.entity;

import com.ohdab.entity.member.MemberJpa;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TEACHER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "teacher_id")
    private Long id;

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
