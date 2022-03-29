package com.rest.api.repository;

import com.rest.api.model.entity.summoner.Match;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchJpaRepo extends JpaRepository<Match, Long> {

    @Transactional
    void deleteAllByMatchAccountId(String summonerAccountId);
}

