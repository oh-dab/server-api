package com.ohdab.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "WRONG_ANSWEWR_NOTE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WrongAnswerNoteJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "wrong_answer_note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentJpa studentJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workbook_id")
    private WorkbookJpa workbookJpa;

    @Column(name = "wrong_number")
    private int wrongNumber;

    @Column(name = "wrong_count")
    private int wrongCount;

    @Builder
    public WrongAnswerNoteJpa(
            StudentJpa studentJpa, WorkbookJpa workbookJpa, int wrongNumber, int wrongCount) {
        this.studentJpa = studentJpa;
        this.workbookJpa = workbookJpa;
        this.wrongNumber = wrongNumber;
        this.wrongCount = wrongCount;
    }
}
