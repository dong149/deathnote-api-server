package com.rest.api.service.summoner;

import com.rest.api.dto.SummonerDto;
import com.rest.api.entity.Summoner;
import com.rest.api.repository.SummonerJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Column;

@RequiredArgsConstructor
@Service
public class SummonerService {


    private final SummonerJpaRepo summonerJpaRepo;


    public Summoner createSummoner(SummonerDto summonerDto){


        Summoner summoner = Summoner.builder()
                .name(summonerDto.getName())
                .accountId(summonerDto.getAccountId())
                .summonerId(summonerDto.getId())
                .profileIconId(summonerDto.getProfileIconId())
                .summonerLevel(summonerDto.getSummonerLevel())
                .build();

        summonerJpaRepo.save(summoner);
        return summoner;
    }






}
