package com.rest.api.repository;

import com.rest.api.entity.summoner.Summoner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SummonerJpaRepo extends JpaRepository<Summoner, String> {

    @Transactional(readOnly = true)
    @Query("SELECT s FROM Summoner as s ORDER BY s.trollerScore ASC")
    List<Summoner> findTrollerRanker(@Param("num") int num, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT s FROM Summoner as s where s.summonerDecodedName like :keyword%")
    List<Summoner> search(@Param("keyword") String keyword, Pageable pageable);

//    @Query("SELECT s.summonerName,s.profileIconId,s.summonerRank,s.summonerTier FROM Summoner as s where s.accountId=:accountId")
    Optional<SummonerToNoteFieldMapper> getByAccountId(String accountId);

    List<Summoner> findAll();
}
