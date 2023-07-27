package com.ohdab.mistakenote.domain;

import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MISTAKENOTE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MistakeNote extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mistakenote_id", nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "workbook_id", nullable = false))
    private WorkbookId workbookId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "student_id", nullable = false))
    private StudentId studentId;

    @ElementCollection
    @CollectionTable(name = "MISTAKE_RECORD", joinColumns = @JoinColumn(name = "mistakenote_id"))
    @MapKeyColumn(name = "problem_number")
    @Column(name = "count")
    private Map<Integer, Integer> mistakeRecords = new HashMap<>();

    @Builder
    public MistakeNote(
            WorkbookId workbookId, StudentId studentId, Map<Integer, Integer> mistakeRecords) {
        this.workbookId = workbookId;
        this.studentId = studentId;
        this.mistakeRecords = mistakeRecords;
    }

    // TODO: refactoring
    public void addMistakeNumbers(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("예외");
        }
        for (int number : numbers) {
            if (mistakeRecords.containsKey(number)) {
                mistakeRecords.put(number, mistakeRecords.get(number) + 1);
                continue;
            }
            mistakeRecords.put(number, 1);
        }
    }
}