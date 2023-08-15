package com.ohdab.workbook.repository;

import com.ohdab.classroom.domain.classroomid.ClassroomId;
import com.ohdab.workbook.domain.Workbook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkbookRepository extends JpaRepository<Workbook, Long> {

    List<Workbook> findByClassroomId(ClassroomId id);

    boolean existsByClassroomIdAndWorkbookInfoName(ClassroomId classroomId, String name);
}
