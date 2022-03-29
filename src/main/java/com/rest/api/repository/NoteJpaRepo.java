package com.rest.api.repository;

import com.rest.api.model.entity.note.Note;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NoteJpaRepo extends JpaRepository<Note, Long> {

    @Transactional(readOnly = true)
    List<Note> findAllByNoteAccountId(String accountId);

    @Transactional(readOnly = true)
    @Query("SELECT n FROM Note as n")
    List<Note> findNoteListRecent(Pageable pageable);
}
