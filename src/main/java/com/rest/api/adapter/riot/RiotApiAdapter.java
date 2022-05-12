package com.rest.api.adapter.riot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.enumerator.QueueType;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import com.rest.api.model.dto.LeagueEntryDto;
import com.rest.api.model.dto.MatchDto;
import com.rest.api.model.dto.SummonerDto;
import com.rest.api.model.dto.mldata.DataRankDto;
import com.rest.api.util.DataRankUtils;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@Service
@RequiredArgsConstructor
public class RiotApiAdapter {

    @Value("${riot.API_KEY}")
    private String API_KEY;

    @Value("${lol.current.season}")
    private int CURRENT_SEASON;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SummonerDto getSummonerDtoBySummonerName(String summonerName) {
        try {
            String requestUrl = UriComponentsBuilder.fromUriString(
                                                        "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/")
                                                    .path(
                                                        summonerName.replaceAll(" ", ""))
                                                    .queryParam("api_key", API_KEY)
                                                    .toUriString();
            URI uri = new URI(requestUrl);
            log.info("get summoner request url : {}", requestUrl);

            return objectMapper.readValue(
                restTemplate.getForEntity(uri, String.class).getBody(),
                SummonerDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("존재하지 않는 Summoner입니다.");
        }
    }

    public List<String> getMatchListDto(String puuid) {
        return getMatchListDto(puuid, QueueType.SOLO_RANK_QUEUE, CURRENT_SEASON);
    }

    public List<String> getMatchListDto(
        String puuid, QueueType queueType, int season) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid
                                   + "/ids")
                .queryParam("queue", queueType.getQueue())
                .queryParam("api_key", API_KEY)
                .queryParam("type", "ranked")
                .queryParam("start", 0)
                .queryParam("count", 10)
                .toUriString();
            URI uri = new URI(requestUrl);
            log.info("get match request url : {}", requestUrl);

            ResponseEntity<List<String>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                });

            return response.getBody();
        } catch (Exception e) {
            log.error("get match ids error : {}", e.getMessage());
            throw new SummonerNotFoundException("매치 List 정보가 존재하지 않습니다.");
        }
    }

    public MatchDto getMatchDtoByMatchId(String matchId) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://asia.api.riotgames.com/lol/match/v5/matches/")
                .path(matchId)
                .queryParam("api_key", API_KEY)
                .toUriString();
            URI uri = new URI(requestUrl);
            log.info("get match by match id request url : {}, match id : {}", requestUrl, matchId);

            return restTemplate.getForObject(uri, MatchDto.class);
        } catch (Exception e) {
            log.error("get match by match id error : {}", e.getMessage());
            throw new SummonerNotFoundException("매치 정보가 존재하지 않습니다.");
        }
    }

    public LeagueEntryDto getLeagueEntryDtoByEncryptedId(String encryptedId) {
        try {
            String requestUrl = UriComponentsBuilder
                .fromUriString("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                .path(encryptedId)
                .queryParam("api_key", API_KEY)
                .toUriString();
            URI uri = new URI(requestUrl);
            return objectMapper
                .readValue(
                    Objects.requireNonNull(
                               restTemplate.getForEntity(uri, String.class).getBody())
                           .replace("[", "").replace("]", ""), LeagueEntryDto.class);
        } catch (Exception e) {
            throw new SummonerNotFoundException("리그 정보가 존재하지 않습니다.");
        }
    }

    public List<DataRankDto> getDateRankDtoByMatchId(String matchId) {
        try {
            MatchDto matchDto = getMatchDtoByMatchId(matchId);
            DataRankUtils dataRankUtils = new DataRankUtils();
            return dataRankUtils.getDataRank(matchDto);
        } catch (Exception e) {
            throw new SummonerNotFoundException("DataRankDto List 정보가 존재하지 않습니다.");
        }
    }
}
