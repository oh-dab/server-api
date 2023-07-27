package com.ohdab.mistakenote.repository;

import com.ohdab.mistakenote.domain.MistakeNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MistakeNoteRepository extends JpaRepository<MistakeNote, Long> {

}
