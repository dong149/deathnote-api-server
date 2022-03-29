package com.rest.api.repository;

import com.rest.api.model.entity.summoner.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchJpaRepo extends JpaRepository<Match, Long> {

    void deleteAllByMatchAccountId(String summonerAccountId);
}

