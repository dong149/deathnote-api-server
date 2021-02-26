package com.rest.api.controller;


import com.rest.api.dto.MatchDto;
import com.rest.api.dto.MatchListDto;
import com.rest.api.dto.MatchReferenceDto;
import com.rest.api.dto.SummonerDto;
import com.rest.api.util.MatchScore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

    private MatchScore matchScore = new MatchScore();

    @GetMapping(value = "/troller")
    public List<Integer> getTrollerScore(@RequestParam String name){
        List<Integer> trollerRankList = new ArrayList<Integer>();

        summonerDto = riotAPIController.getSummoner(name);
        if(!summonerDto.equals(null)){
            String accountId = summonerDto.getAccountId();
            matchListDto = riotAPIController.getMatchList(accountId);
            List<MatchReferenceDto> matchReferenceDtoList = matchListDto.getMatches();
            for(int i=0;i<matchReferenceDtoList.size();i++){
                if(matchReferenceDtoList.get(i).getSeason()<13||i>20){
                    continue;
                }
                long gameId = matchReferenceDtoList.get(i).getGameId();
                matchDto = riotAPIController.getMatchInfo(gameId);
                trollerRankList.add(matchScore.getMatchScore(matchDto,accountId));
            }

        }


        return trollerRankList;

    }


}
