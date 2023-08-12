package com.ohdab.workbook.domain;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.core.baseentity.BaseEntity;
import com.ohdab.workbook.domain.workbookInfo.WorkbookInfo;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WORKBOOK")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workbook extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "workbook_id", nullable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "workbook_name", nullable = false))
    @AttributeOverride(name = "description", column = @Column(name = "workbook_description"))
    @AttributeOverride(name = "startingNumber", column = @Column(name = "workbook_starting_number"))
    @AttributeOverride(name = "endingNumber", column = @Column(name = "workbook_ending_number"))
    private WorkbookInfo workbookInfo;

    @Embedded
    @AttributeOverride(
            name = "id",
            column = @Column(name = "classroom_teacher_id", nullable = false))
    private ClassroomId classroomId;

    @Builder
    public Workbook(WorkbookInfo workbookInfo, ClassroomId classroomId) {
        setWorkbookInfo(workbookInfo);
        this.classroomId = classroomId;
    }

    private void setWorkbookInfo(WorkbookInfo workbookInfo) {
        if (workbookInfo == null) {
            throw new IllegalArgumentException("예외");
        }
        this.workbookInfo = workbookInfo;
    }

    public void updateWorkbookInfo(WorkbookInfo workbookInfo) {
        if (workbookInfo == null) {
            throw new IllegalStateException("WorkbookInfo cannot be null");
        }
        this.workbookInfo = workbookInfo;
    }
}
