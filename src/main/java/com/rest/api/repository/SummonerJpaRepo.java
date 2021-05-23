package com.rest.api.repository;

import com.rest.api.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SummonerJpaRepo extends JpaRepository<Summoner, Long> {
}
