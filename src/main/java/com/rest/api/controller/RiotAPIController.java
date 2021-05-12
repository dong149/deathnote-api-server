package com.rest.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.dto.*;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.util.DataRank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"1. Summoner"})
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듭니다.
@RestController
@RequestMapping(value = "/v1")
public class RiotAPIController {

    private final static Logger logger = LoggerFactory.getLogger(RiotAPIController.class);
    @Autowired
    RestTemplate restTemplate;


//    private static String API_KEY = "RGAPI-e5fc0066-50ea-486a-8133-50cf8eff1aad";

    @Value("${riot.API_KEY}")
    private static String API_KEY;


    @ApiOperation(value = "소환자 정보", notes = "이름을 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/summoner")
    public static SummonerDto getSummoner(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {

        ObjectMapper objectMapper = new ObjectMapper();


        //한글 인코딩 처리
        String SummonerName = "";
        try {
            SummonerName = URLEncoder.encode(name, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 공백 처리
        SummonerName = SummonerName.replaceAll(" ", "%20");

        String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + SummonerName + "?api_key=" + API_KEY;
        SummonerDto summoner = new SummonerDto();

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);

                summoner = objectMapper.readValue(body, SummonerDto.class);   // String to Object로 변환

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(String.valueOf(summoner));

        return summoner;
    }


    @ApiOperation(value = "소환자 정보", notes = "account ID를 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/matchlist")
    public MatchListDto getMatchList(@ApiParam(value = "소환사 이름", required = true) @RequestParam String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 공백 처리
        String accountId = id.replaceAll(" ", "%20");

        String requestURL = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/" + accountId + "?api_key=" + API_KEY;
        MatchListDto matchList = new MatchListDto();

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);

                matchList = objectMapper.readValue(body, MatchListDto.class);   // String to Object로 변환

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug(String.valueOf(matchList));

        return matchList;
    }

    @ApiOperation(value = "소환자 정보", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
    @GetMapping(value = "/match")
    public MatchDto getMatchInfo(@ApiParam(value = "MatchId", required = true) @RequestParam long Id) {

        ObjectMapper objectMapper = new ObjectMapper();

        String requestURL = "https://kr.api.riotgames.com/lol/match/v4/matches/" + Id + "?api_key=" + API_KEY;
        MatchDto matchDto = new MatchDto();

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);

                matchDto = objectMapper.readValue(body, MatchDto.class);   // String to Object로 변환

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(String.valueOf(matchDto));

        return matchDto;
    }


    @ApiOperation(value = "소환사 정보", notes = "id 를 이용하여, 리그 정보를 알아냅니다.")
    @GetMapping(value = "/league")
    public LeagueEntryDto getLeagueInfo(@ApiParam(value = "EncryptedSummonerId", required = true) @RequestParam String Id) {

        ObjectMapper objectMapper = new ObjectMapper();

        String requestURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + Id + "?api_key=" + API_KEY;
        LeagueEntryDto leagueEntryDto = new LeagueEntryDto();

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                //set type으로 반환되어서, 양 끝 괄호를 없앤다.
                body = body.replace("[", "");
                body = body.replace("]", "");
                leagueEntryDto = objectMapper.readValue(body, LeagueEntryDto.class);   // String to Object로 변환

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(String.valueOf(leagueEntryDto));

        return leagueEntryDto;
    }


    /*
     ML 모델을 구축하기 위해서 Match 각각의 정보를 가져온 이후에, 10명의 유저에 대해서 각각의 지표들의 Rank( 1~10 )를 연산한 이후에
     DB에 저장해줘야 한다.
     queue : 420 -> RANK게임을 의미한다.


     각 지표에 대한 랭킹 정보를 담은 DTO를 하나 만들고 그 값을 DB에 저장해주어야한다.
     */
//    @ApiOperation(value = "소환자 정보", notes = "MatchId를 이용하여, Match 정보를 가져옵니다.")
//    @GetMapping(value = "/match/data")
    public List<DataRankDto> getMatchInfoForML(String Id) {

        ObjectMapper objectMapper = new ObjectMapper();

        String requestURL = "https://kr.api.riotgames.com/lol/match/v4/matches/" + Id + "?api_key=" + API_KEY;
        MatchDto matchDto = new MatchDto();
        DataRank dataRank = new DataRank();
        List<DataRankDto> resList = new ArrayList<>();
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);

                matchDto = objectMapper.readValue(body, MatchDto.class);   // String to Object로 변환
                resList = dataRank.getDataRank(matchDto);

            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug(String.valueOf(matchDto));

        return resList;
    }

    /*
    솔로랭크인 게임( queue : 420 )의 정보를 리스트에 담아 Return 합니다.
     */
    public List<Long> getMatchListForML(String summonerName) {
        List<MatchReferenceDto> matchList = getMatchList(getSummoner(summonerName).getAccountId()).getMatches();
        List<Long> matchIdList = new ArrayList<>();
        for (int i = 0; i < matchList.size(); i++) {
            if (matchList.get(i).getQueue() == 420) {
                matchIdList.add(matchList.get(i).getGameId());
            }
        }
        return matchIdList;
    }
}
