package com.rest.api.service.v2.deathnote;

import com.rest.api.adapter.riot.RiotApiAdapter;
import com.rest.api.enumerator.QueueType;
import com.rest.api.model.dto.LeagueEntryDto;
import com.rest.api.model.dto.SummonerDto;
import com.rest.api.model.dto.result.SummonerInfoDto;
import com.rest.api.model.dto.result.SummonerMatchDto;
import com.rest.api.model.entity.summoner.Match;
import com.rest.api.model.entity.summoner.Summoner;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.service.v1.deathnote.DeathnoteServiceHelper;
import com.rest.api.util.DeathnoteUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class V2DeathnoteService {
    
    private final RiotApiAdapter riotApiAdapter;
    private final SummonerJpaRepo summonerJpaRepo;
    private final ModelMapper modelMapper;
    private final ExecutorService executor;
    
    @Transactional
    public SummonerInfoDto getSummonerInfoDto(String name, boolean reload) {
        SummonerDto summonerDto = riotApiAdapter.getSummonerDtoBySummonerName(name);
        
        // 기존에 Summoner 가 존재하고 reload 가 필요없으면 기존 값을 return 해준다.
        Summoner summoner = summonerJpaRepo.findById(summonerDto.getAccountId()).orElse(null);
        if (ObjectUtils.isNotEmpty(summoner) && !reload) {
            return getSummonerInfoDtoExist(summoner);
        }
        
        List<String> matchIds = riotApiAdapter.getMatchListDto(
            summonerDto.getPuuid(),
            QueueType.SOLO_RANK_QUEUE,
            DeathnoteUtils.CURRENT_SEASON);
        
        LeagueEntryDto leagueEntryDto = riotApiAdapter.getLeagueEntryDtoByEncryptedId(summonerDto.getId());
        
        List<SummonerMatchDto> summonerMatchDtos = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        List<Future<SummonerMatchDto>> futures = new ArrayList<>();
        
        for (String matchId : matchIds) {
            Callable<SummonerMatchDto> callable = () ->
                                                      DeathnoteServiceHelper.getMatchScore(
                                                          riotApiAdapter.getMatchDtoByMatchId(matchId),
                                                          summonerDto.getAccountId());
            
            futures.add(executor.submit(callable));
        }
        
        return null;
    }
    
    private SummonerInfoDto getSummonerInfoDtoExist(Summoner summoner) {
        List<SummonerMatchDto> summonerMatchDtos = new ArrayList<>();
        
        List<Match> matches = summoner.getMatches();
        for (Match match : matches) {
            summonerMatchDtos.add(modelMapper.map(match, SummonerMatchDto.class));
        }
        
        return SummonerInfoDto.of(summoner, summonerMatchDtos);
    }
}
