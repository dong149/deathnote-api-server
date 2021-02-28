package com.rest.api.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
    /**
        1. troller 점수 ( 100점 만점 )
        2. summoner 아이콘
        3. summoner 랭크 정보
        4. 매게임 정보 (랭크 게임만 해당)
        ( 매 판 등수, 어떤 챔피언을 선택했는지, kda 정보 )
     **/
    private int trollerScore;
    private int matchCount;
    private int matchWin;
    private int matchLose;
    private int summonerIcon;
    private String summonerTier;
    private String summonerRank;
    private List<SummonerMatchDto> summonerMatch;


}
