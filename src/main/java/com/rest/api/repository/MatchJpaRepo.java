package com.rest.api.repository;

import com.rest.api.entity.summoner.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface MatchJpaRepo extends JpaRepository<Match,Long> {
    @Transactional
    void deleteAllByMatchAccountId(String summonerAccountId);
}
