package com.rest.api.model.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.api.model.entity.summoner.Summoner;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerInfoDto {

    private String summonerName;
    private String accountId;
    private int trollerScore;
    private int matchCount;
    private int matchWinningRate;
    private int matchWin;
    private int matchLose;
    private long summonerLevel;
    private int summonerIcon;
    private String summonerTier;
    private String summonerRank;
    private List<SummonerMatchDto> summonerMatch;
    private LocalDateTime updatedAt;

    public static SummonerInfoDto of(
        Summoner summoner,
        List<SummonerMatchDto> summonerMatchDtos) {

        return SummonerInfoDto.builder()
                              .summonerName(summoner.getSummonerName())
                              .accountId(summoner.getAccountId())
                              .trollerScore(summoner.getTrollerScore())
                              .summonerTier(summoner.getSummonerTier())
                              .summonerRank(summoner.getSummonerRank())
                              .summonerMatch(summonerMatchDtos)
                              .summonerLevel(summoner.getSummonerLevel())
                              .summonerIcon(summoner.getProfileIconId())
                              .matchWinningRate(summoner.getMatchWinningRate())
                              .matchLose(summoner.getMatchLose())
                              .matchWin(summoner.getMatchWin())
                              .matchCount(summoner.getMatchCount())
                              .updatedAt(summoner.getUpdatedAt())
                              .build();
    }
}
