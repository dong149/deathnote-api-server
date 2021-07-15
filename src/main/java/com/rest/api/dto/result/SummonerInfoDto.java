package com.rest.api.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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


}
