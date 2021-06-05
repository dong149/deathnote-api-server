package com.rest.api.repository;

import com.rest.api.entity.summoner.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchJpaRepo extends JpaRepository<Match,Long> {

}
