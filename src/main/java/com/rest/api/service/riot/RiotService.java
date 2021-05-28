package com.rest.api.service.riot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.dto.LeagueEntryDto;
import com.rest.api.dto.MatchDto;
import com.rest.api.dto.MatchListDto;
import com.rest.api.dto.SummonerDto;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.util.DataRank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class RiotService {


    @Value("${riot.API_KEY}")
    private String API_KEY;


    private final RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public SummonerDto getSummonerDtoWithRiotAPIBySummonerName(String summonerName) throws URISyntaxException, IOException {
        String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/")
                .path(summonerName.replaceAll(" ", ""))
                .queryParam("api_key", API_KEY)
                .toUriString();
        URI uri = new URI(requestUrl);
        return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(), SummonerDto.class);
    }

    public MatchListDto getMatchListDtoWithRiotAPIByEncryptedAccountId(String encryptedAccountId) throws URISyntaxException, IOException {
        String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/")
                .path(encryptedAccountId)
                .queryParam("api_key", API_KEY)
                .toUriString();
        URI uri = new URI(requestUrl);
        return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(), MatchListDto.class);
    }


    public MatchDto getMatchDtoWithRiotAPIByMatchId(long matchId) throws URISyntaxException, IOException {
        String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/match/v4/matches/")
                .path(String.valueOf(matchId))
                .queryParam("api_key", API_KEY)
                .toUriString();

        URI uri = new URI(requestUrl);
        return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(), MatchDto.class);
    }


    public LeagueEntryDto getLeagueEntryDtoWithRiotAPIByEncryptedId(String encryptedId) throws URISyntaxException, IOException {
        String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                .path(encryptedId)
                .queryParam("api_key", API_KEY)
                .toUriString();
        URI uri = new URI(requestUrl);
        return objectMapper
                .readValue(Objects.requireNonNull(restTemplate.getForEntity(uri, String.class).getBody()).replace("[", "").replace("]", ""), LeagueEntryDto.class);
    }

    public List<DataRankDto> getDataRankDtosMLWithRiotAPIByMatchId(String MatchId) throws IOException, URISyntaxException {
        MatchDto matchDto = getMatchDtoWithRiotAPIByMatchId(Long.parseLong(MatchId));
        DataRank dataRank = new DataRank();
        return dataRank.getDataRank(matchDto);
    }


}
