package com.rest.api.controller;


import com.rest.api.dto.*;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.service.summonerInfo.SummonerInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듭니다.
@RestController
@RequestMapping(value = "/v1")
public class TrollerController {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SummonerDto summonerDto;
    private MatchListDto matchListDto;
    private MatchDto matchDto;
    @Autowired
    private RiotAPIController riotAPIController;

    private SummonerInfoService matchScore = new SummonerInfoService();

    @GetMapping(value = "/summoner/info")
    public SummonerInfoDto getSummonerInfo(@RequestParam String name) {
        int matchCnt = 0;
        int matchScoreSum = 0;
        int matchFinalScore = 0;
        int matchWin = 0;
        int matchLose = 0;
        SummonerInfoDto summonerInfoDto = new SummonerInfoDto();
        LeagueEntryDto leagueEntryDto = new LeagueEntryDto();
        SummonerMatchDto summonerMatchDto = new SummonerMatchDto();
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<SummonerMatchDto>();
        summonerDto = riotAPIController.getSummoner(name);


        if (!summonerDto.equals(null)) {
            String accountId = summonerDto.getAccountId();
            String Id = summonerDto.getId();
            matchListDto = riotAPIController.getMatchList(accountId);
            leagueEntryDto = riotAPIController.getLeagueInfo(Id);

            // matchWin, matchLoss , matchCnt 값을 저장합니다.
            matchWin = leagueEntryDto.getWins();
            matchLose = leagueEntryDto.getLosses();

            List<MatchReferenceDto> matchReferenceDtoList = matchListDto.getMatches();
            //TODO 제약사항 더 추가하기
            for (int i = 0; i < matchReferenceDtoList.size(); i++) {
                if (matchReferenceDtoList.get(i).getSeason() < 13 || i > 20) {
                    continue;
                }
                long gameId = matchReferenceDtoList.get(i).getGameId();
                matchDto = riotAPIController.getMatchInfo(gameId);
                if (!matchDto.getGameMode().equals("CLASSIC"))
                    continue;
                summonerMatchDto = matchScore.getMatchScore(matchDto, accountId);
                summonerMatchDtoList.add(summonerMatchDto);
                matchCnt++;
                if (summonerMatchDto.isMatchWin()) {
                    matchScoreSum += summonerMatchDto.getMatchRank();
                } else {
                    matchScoreSum += summonerMatchDto.getMatchRank() - 1;
                }

            }


        }
        matchFinalScore = (int) (11 - (matchScoreSum / matchCnt) * 1.0) * 10;

        // summonerInfoDto에 값을 입력합니다.
        summonerInfoDto.setSummonerName(summonerDto.getName());
        summonerInfoDto.setTrollerScore(matchFinalScore);
        summonerInfoDto.setMatchCount(matchCnt);
        summonerInfoDto.setMatchWinningRate((int) ((1.0) * matchWin / (matchWin + matchLose) * 100));
        summonerInfoDto.setMatchWin(matchWin);
        summonerInfoDto.setMatchLose(matchLose);
        summonerInfoDto.setSummonerLevel(summonerDto.getSummonerLevel());
        summonerInfoDto.setSummonerMatch(summonerMatchDtoList);
        summonerInfoDto.setSummonerIcon(summonerDto.getProfileIconId());
        summonerInfoDto.setSummonerTier(leagueEntryDto.getTier());
        summonerInfoDto.setSummonerRank(leagueEntryDto.getRank());


        return summonerInfoDto;

    }


}
