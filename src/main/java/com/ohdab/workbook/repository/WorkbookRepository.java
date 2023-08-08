package com.ohdab.workbook.repository;

import com.ohdab.workbook.domain.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkbookRepository extends JpaRepository<Workbook, Long> {}
