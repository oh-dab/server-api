package com.ohdab.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "WORKBOOK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkbookJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "workbook_id")
    private Long id;

    @Column(name = "startingNumber")
    private int startingNumber;

    @Column(name = "endingNumber")
    private int endingNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private ClassroomJpa classroomJpa;

    @Builder
    public WorkbookJpa(
            int startingNumber,
            int endingNumber,
            String name,
            String description,
            ClassroomJpa classroomJpa) {
        this.startingNumber = startingNumber;
        this.endingNumber = endingNumber;
        this.name = name;
        this.description = description;
        this.classroomJpa = classroomJpa;
    }
}
