package com.rest.api.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerMatchDto {
    /**
     4. 매게임 정보 (랭크 게임만 해당)
     ( 매 판 등수, 어떤 챔피언을 선택했는지, kda 정보 )
     **/
    private int matchRank;
    private boolean matchWin;
    private int matchChampion;
    private int matchKills;
    private int matchDeaths;
    private int matchAssists;
    private int matchDealRank;
    private int matchTankRank;
    private int matchKdaScoreRank;
    private int matchTowerDealRank;


}
