package com.ohdab.mistakenote.domain;

import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.workbook.domain.Workbook;
import com.ohdab.workbook.domain.service.NumberScopeCheckService;
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
        setMistakeRecords(mistakeRecords);
    }

    private void setMistakeRecords(Map<Integer, Integer> mistakeRecords) {
        if (mistakeRecords == null) mistakeRecords = new HashMap<>();
        this.mistakeRecords = mistakeRecords;
    }

    @Builder
    public MistakeNote(WorkbookId workbookId, StudentId studentId) {
        this.workbookId = workbookId;
        this.studentId = studentId;
    }

    public void addMistakeNumbers(
            NumberScopeCheckService numberScopeCheckService,
            Workbook workbook,
            List<Integer> numbers) {
        numberScopeCheckService.numberScopeCheck(workbook, numbers);
        checkMistakeNumbersSize(numbers);
        updateWrongCount(numbers);
    }

    private void checkMistakeNumbersSize(List<Integer> numbers) {
        if (numbers.isEmpty() || numbers.size() > 500) {
            throw new IllegalArgumentException("틀린 문제 번호는 최소 1개 이상, 500개 이하로 입력할 수 있습니다.");
        }
    }

    private void updateWrongCount(List<Integer> numbers) {
        numbers.forEach(
                number -> {
                    if (mistakeRecords.containsKey(number)) {
                        mistakeRecords.put(number, mistakeRecords.get(number) + 1);
                    } else {
                        mistakeRecords.put(number, 1);
                    }
                });
    }
}
