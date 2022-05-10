package com.rest.api.controller.riot;

import com.rest.api.adapter.riot.RiotApiAdapter;
import com.rest.api.model.dto.LeagueEntryDto;
import com.rest.api.model.dto.MatchDto;
import com.rest.api.model.dto.SummonerDto;
import com.rest.api.model.dto.mldata.DataRankDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Summoner"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class RiotAPIController {

    // TODO : DB에 저장
    private final RiotApiAdapter riotApiAdapter;

    @ApiOperation(value = "소환자 정보", notes = "이름을 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/summoner")
    public SummonerDto getSummoner(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {
        return riotApiAdapter.getSummonerDtoWithRiotAPIBySummonerName(name);
    }

    @ApiOperation(value = "소환자 정보", notes = "puuid를 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/matchlist")
    public List<String> getMatchIds(
        @ApiParam(value = "소환사 uuid", required = true) @RequestParam String puuid) {

        List<String> matchIds = null;
        try {
            matchIds = riotApiAdapter.getMatchListDto(puuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchIds;
    }

    @ApiOperation(value = "소환자 정보", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
    @GetMapping(value = "/match")
    public MatchDto getMatchInfo(@ApiParam(value = "MatchId", required = true) @RequestParam String matchId) {

        MatchDto matchDto = null;
        try {
            matchDto = riotApiAdapter.getMatchDtoWithRiotAPIByMatchId(matchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchDto;
    }

    @ApiOperation(value = "테스트", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
    @GetMapping(value = "/match/test")
    public MatchDto getTest(@ApiParam(value = "MatchId", required = true) @RequestParam String matchId) {
        MatchDto matchDto = null;
        try {
            matchDto = riotApiAdapter.getTestDto(matchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchDto;
    }

    @ApiOperation(value = "소환사 정보", notes = "id 를 이용하여, 리그 정보를 알아냅니다.")
    @GetMapping(value = "/league")
    public LeagueEntryDto getLeagueInfo(
        @ApiParam(value = "EncryptedSummonerId", required = true) @RequestParam String encryptedId) {

        LeagueEntryDto leagueEntryDto = null;
        try {
            leagueEntryDto = riotApiAdapter.getLeagueEntryDtoWithRiotAPIByEncryptedId(encryptedId);
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
            dataRankDtoList = riotApiAdapter.getDataRankDtosMLWithRiotAPIByMatchId(MatchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataRankDtoList;
    }

    @ApiOperation(value = "소환자 정보", notes = "summonerName을 통해 솔로 랭크 게임 리스트를 가져옵니디")
    @GetMapping(value = "/matchlist/solo")
    public List<String> getMatchListForML(String summonerName) {
        return getMatchIds(getSummoner(summonerName).getPuuid());
    }
}