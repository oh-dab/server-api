package com.ohdab.mistakenote.repository;

import com.ohdab.member.domain.student.studentid.StudentId;
import com.ohdab.mistakenote.domain.MistakeNote;
import com.ohdab.workbook.domain.workbookid.WorkbookId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MistakeNoteRepository extends JpaRepository<MistakeNote, Long> {

    Optional<MistakeNote> findByWorkbookIdAndStudentId(WorkbookId workbookId, StudentId studentId);
}
