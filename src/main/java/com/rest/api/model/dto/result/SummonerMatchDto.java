package com.rest.api.model.dto.result;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.api.model.entity.summoner.Match;
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
public class SummonerMatchDto {

    private int matchRank;
    private boolean matchWin;
    private int matchChampion;
    private int matchKills;
    private int matchDeaths;
    private int matchAssists;
    // 새롭게 추가했습니다.
    private int kdaRank;
    private int totalDamageDealtToChampionsRank;
    private int totalDamageTakenRank;
    private int visionScoreRank;
    private int damageDealtToTurretsRank;
    private int totalUnitsHealedRank;
    private int goldEarnedRank;
    private int champLevelRank;
    private int damageDealtToObjectivesRank;
    private int neutralMinionsKilledRank;
    private int magicDamageDealtToChampionsRank;
    private int wardsKilledRank;
    private int damageSelfMitigatedRank;
    private int largestCriticalStrikeRank;
    private int nodeNeutralizeRank;
    private int totalTimeCrowdControlDealtRank;
    private int wardsPlacedRank;
    private int totalDamageDealtRank;
    private int timeCCingOthersRank;
    private int magicalDamageTakenRank;
    private int physicalDamageDealtToChampionsRank;
    private int neutralMinionsKilledTeamJungleRank;
    private int totalMinionsKilledRank;
    private int visionWardsBoughtInGameRank;
    private int trueDamageTakenRank;
    private int totalHealRank;
    private int longestTimeSpentLivingRank;
    private int killingSpreesRank;
    private int neutralMinionsKilledEnemyJungleRank;
    private int trueDamageDealtRank;

    public Match toEntity(String accountId) {
        return new Match(
            accountId,
            this.matchRank,
            this.matchWin,
            this.matchChampion,
            this.matchKills,
            this.matchDeaths,
            this.matchAssists,
            this.kdaRank,
            this.totalDamageDealtToChampionsRank,
            this.totalDamageTakenRank,
            this.visionScoreRank,
            this.damageDealtToTurretsRank,
            this.totalUnitsHealedRank,
            this.goldEarnedRank,
            this.champLevelRank,
            this.damageDealtToObjectivesRank,
            this.neutralMinionsKilledRank,
            this.magicDamageDealtToChampionsRank,
            this.wardsKilledRank,
            this.damageSelfMitigatedRank,
            this.largestCriticalStrikeRank,
            this.nodeNeutralizeRank,
            this.totalTimeCrowdControlDealtRank,
            this.wardsPlacedRank,
            this.totalDamageDealtRank,
            this.timeCCingOthersRank,
            this.magicalDamageTakenRank,
            this.physicalDamageDealtToChampionsRank,
            this.neutralMinionsKilledTeamJungleRank,
            this.totalMinionsKilledRank,
            this.visionWardsBoughtInGameRank,
            this.trueDamageTakenRank,
            this.totalHealRank,
            this.longestTimeSpentLivingRank,
            this.killingSpreesRank,
            this.neutralMinionsKilledEnemyJungleRank,
            this.trueDamageDealtRank
        );
    }
}
