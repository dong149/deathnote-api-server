package com.rest.api.repository;

import com.rest.api.entity.note.Note;
import com.rest.api.entity.summoner.Summoner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface NoteJpaRepo  extends JpaRepository<Note,Long> {

    @Transactional(readOnly=true)
    List<Note> findAllByNoteAccountId(String accountId);


    @Transactional(readOnly = true)
    @Query("SELECT n FROM Note as n")
    List<Note> findNoteListRecent(Pageable pageable);
}
