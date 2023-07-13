package com.ohdab.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberJpa memberJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private ClassroomJpa classroomJpa;

    @Builder
    public StudentJpa(MemberJpa memberJpa, ClassroomJpa classroomJpa) {
        this.memberJpa = memberJpa;
        this.classroomJpa = classroomJpa;
    }
}
