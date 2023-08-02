package com.ohdab.mistakenote.repository;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MistakeNoteRepository extends JpaRepository<MistakeNote, Long> {

    Optional<MistakeNote> findByWorkbookIdAndStudentId(WorkbookId workbookId, StudentId studentId);

    List<MistakeNote> findByWorkbookId(WorkbookId workbookId);
}
