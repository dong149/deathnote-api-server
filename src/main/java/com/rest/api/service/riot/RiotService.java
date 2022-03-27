package com.rest.api.service.riot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.dto.LeagueEntryDto;
import com.rest.api.dto.MatchDto;
import com.rest.api.dto.MatchListDto;
import com.rest.api.dto.SummonerDto;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.enumerator.QueueType;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import com.rest.api.util.DataRank;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@RequiredArgsConstructor
@Service
public class RiotService {

    @Value("${riot.API_KEY}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public SummonerDto getSummonerDtoWithRiotAPIBySummonerName(String summonerName) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/")
                .path(summonerName.replaceAll(" ", ""))
                .queryParam("api_key", API_KEY)
                .toUriString();
            URI uri = new URI(requestUrl);
            return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(),
                                          SummonerDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("존재하지 않는 Summoner입니다.");
        }
    }

    public MatchListDto getMatchListDtoWithRiotAPIByEncryptedAccountId(String encryptedAccountId) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/")
                .path(encryptedAccountId)
                .queryParam("api_key", API_KEY)
                .toUriString();
            URI uri = new URI(requestUrl);
            return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(),
                                          MatchListDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("무지성으로 가져오는 매치 List 정보가 존재하지 않습니다. ");
        }
    }


    public MatchListDto getMatchListDtoWithRiotAPIByEncryptedAccountIdAndQueueAndSeason(
        String encryptedAccountId, QueueType queueType, int season) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/")
                .path(encryptedAccountId)
                .queryParam("queue", queueType.getQueue())
                .queryParam("season", season)
                .queryParam("api_key", API_KEY)
                .toUriString();
            System.out.println(requestUrl);
            URI uri = new URI(requestUrl);
            return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(),
                                          MatchListDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("매치 List 정보가 존재하지 않습니다.");
        }
    }


    public MatchDto getMatchDtoWithRiotAPIByMatchId(long matchId) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/match/v4/matches/")
                .path(String.valueOf(matchId))
                .queryParam("api_key", API_KEY)
                .toUriString();

            URI uri = new URI(requestUrl);
            return objectMapper.readValue(restTemplate.getForEntity(uri, String.class).getBody(),
                                          MatchDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("매치 정보가 존재하지 않습니다.");
        }
    }


    public LeagueEntryDto getLeagueEntryDtoWithRiotAPIByEncryptedId(String encryptedId) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                .path(encryptedId)
                .queryParam("api_key", API_KEY)
                .toUriString();
            URI uri = new URI(requestUrl);
            return objectMapper
                .readValue(
                    Objects.requireNonNull(restTemplate.getForEntity(uri, String.class).getBody())
                           .replace("[", "").replace("]", ""), LeagueEntryDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("리그 정보가 존재하지 않습니다.");
        }
    }

    public List<DataRankDto> getDataRankDtosMLWithRiotAPIByMatchId(String MatchId) {
        try {
            MatchDto matchDto = getMatchDtoWithRiotAPIByMatchId(Long.parseLong(MatchId));
            DataRank dataRank = new DataRank();
            return dataRank.getDataRank(matchDto);
        } catch (Exception e) {
            throw new SummonerNotFoundException("DataRankDto List 정보가 존재하지 않습니다.");
        }
    }


}
