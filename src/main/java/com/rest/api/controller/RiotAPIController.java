package com.rest.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.dto.MatchDto;
import com.rest.api.dto.MatchListDto;
import com.rest.api.dto.SummonerDto;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Api(tags = {"1. Summoner"})
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만듭니다.
@RestController
@RequestMapping(value = "/v1")
public class RiotAPIController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplate restTemplate;

    @Value("${riot.API_KEY}")
    private String API_KEY;


    @ApiOperation(value = "소환자 정보", notes = "이름을 통해 소환사 정보를 return한다.")
    @GetMapping(value = "/summoner")
    public SummonerDto getSummoner(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {

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
        System.out.print(requestURL);
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
        System.out.print(requestURL);
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

}
