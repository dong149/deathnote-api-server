package com.rest.api.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.api.entity.summoner.Match;
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

    public Match toEntity(String accountId) {
        return new Match(accountId,this.matchRank,this.matchWin,this.matchChampion,this.matchKills,this.matchDeaths,this.matchAssists,this.matchDealRank,this.matchTankRank,this.matchKdaScoreRank,this.matchTowerDealRank);
    }

}
