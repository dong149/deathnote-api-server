package com.rest.api.controller.riot;

import com.rest.api.dto.*;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.exception.riot.RiotAPIBadRequestException;
import com.rest.api.service.riot.RiotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"1. Summoner"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class RiotAPIController {

    // TODO : DB에 저장
    private final RiotService riotService;

    @ApiOperation(value = "소환자 정보", notes = "이름을 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/summoner")
    public SummonerDto getSummoner(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {

        SummonerDto summonerDto = null;
        try {
            summonerDto = riotService.getSummonerDtoWithRiotAPIBySummonerName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summonerDto;
    }


    @ApiOperation(value = "소환자 정보", notes = "account ID를 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/matchlist")
    public MatchListDto getMatchList(@ApiParam(value = "소환사 이름", required = true) @RequestParam String accountId) {

        MatchListDto matchListDto = null;
        try {
            matchListDto = riotService.getMatchListDtoWithRiotAPIByEncryptedAccountId(accountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchListDto;
    }

    @ApiOperation(value = "소환자 정보", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
    @GetMapping(value = "/match")
    public MatchDto getMatchInfo(@ApiParam(value = "MatchId", required = true) @RequestParam long matchId) {

        MatchDto matchDto = null;
        try {
            matchDto = riotService.getMatchDtoWithRiotAPIByMatchId(matchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchDto;

    }


    @ApiOperation(value = "소환사 정보", notes = "id 를 이용하여, 리그 정보를 알아냅니다.")
    @GetMapping(value = "/league")
    public LeagueEntryDto getLeagueInfo(@ApiParam(value = "EncryptedSummonerId", required = true) @RequestParam String encryptedId) {

        LeagueEntryDto leagueEntryDto = null;
        try {
            leagueEntryDto = riotService.getLeagueEntryDtoWithRiotAPIByEncryptedId(encryptedId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leagueEntryDto;
    }

    @ApiOperation(value = "소환자 정보", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
    @GetMapping(value = "/match/data")
    public List<DataRankDto> getMatchInfoForML(String MatchId) {
        List<DataRankDto> dataRankDtoList = null;
        try {
            dataRankDtoList = riotService.getDataRankDtosMLWithRiotAPIByMatchId(MatchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataRankDtoList;
    }


    @ApiOperation(value = "소환자 정보", notes = "summonerName을 통해 솔로 랭크 게임 리스트를 가져옵니디")
    @GetMapping(value = "/matchlist/solo")
    public List<Long> getMatchListForML(String summonerName) {
        List<MatchReferenceDto> matchList = getMatchList(getSummoner(summonerName).getAccountId()).getMatches();
        List<Long> matchIdList = new ArrayList<>();
        for (int i = 0; i < matchList.size(); i++) {
            //솔로랭크인 게임( queue : 420 )의 정보를 리스트에 담아 Return 합니다.
            if (matchList.get(i).getQueue() == 420) {
                matchIdList.add(matchList.get(i).getGameId());
            }
        }
        return matchIdList;
    }
}
