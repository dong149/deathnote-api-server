package com.rest.api.model.entity.summoner;

import com.rest.api.model.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="matches")
public class Match extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    private String matchAccountId;
    private Integer matchRank;
    private Boolean matchWin;
    private Integer matchChampion;
    private Integer matchKills;
    private Integer matchDeaths;
    private Integer matchAssists;
    private Integer kdaRank;
    private Integer totalDamageDealtToChampionsRank;
    private Integer totalDamageTakenRank;
    private Integer visionScoreRank;
    private Integer damageDealtToTurretsRank;
    private Integer totalUnitsHealedRank;
    private Integer goldEarnedRank;
    private Integer champLevelRank;
    private Integer damageDealtToObjectivesRank;
    private Integer neutralMinionsKilledRank;
    private Integer magicDamageDealtToChampionsRank;
    private Integer wardsKilledRank;
    private Integer damageSelfMitigatedRank;
    private Integer largestCriticalStrikeRank;
    private Integer nodeNeutralizeRank;
    private Integer totalTimeCrowdControlDealtRank;
    private Integer wardsPlacedRank;
    private Integer totalDamageDealtRank;
    private Integer timeCCingOthersRank;
    private Integer magicalDamageTakenRank;
    private Integer physicalDamageDealtToChampionsRank;
    private Integer neutralMinionsKilledTeamJungleRank;
    private Integer totalMinionsKilledRank;
    private Integer visionWardsBoughtInGameRank;
    private Integer trueDamageTakenRank;
    private Integer totalHealRank;
    private Integer longestTimeSpentLivingRank;
    private Integer killingSpreesRank;
    private Integer neutralMinionsKilledEnemyJungleRank;
    private Integer trueDamageDealtRank;

    public Match(
        String matchAccountId, int matchRank, boolean matchWin, int matchChampion, int matchKills,
        int matchDeaths, int matchAssists, int kdaRank, int totalDamageDealtToChampionsRank,
        int totalDamageTakenRank, int visionScoreRank, int damageDealtToTurretsRank, int totalUnitsHealedRank,
        int goldEarnedRank, int champLevelRank, int damageDealtToObjectivesRank, int neutralMinionsKilledRank,
        int magicDamageDealtToChampionsRank, int wardsKilledRank, int damageSelfMitigatedRank,
        int largestCriticalStrikeRank, int nodeNeutralizeRank, int totalTimeCrowdControlDealtRank,
        int wardsPlacedRank, int totalDamageDealtRank, int timeCCingOthersRank, int magicalDamageTakenRank,
        int physicalDamageDealtToChampionsRank, int neutralMinionsKilledTeamJungleRank,
        int totalMinionsKilledRank, int visionWardsBoughtInGameRank, int trueDamageTakenRank,
        int totalHealRank, int longestTimeSpentLivingRank, int killingSpreesRank,
        int neutralMinionsKilledEnemyJungleRank, int trueDamageDealtRank) {
        this.matchAccountId = matchAccountId;
        this.matchRank = matchRank;
        this.matchWin = matchWin;
        this.matchChampion = matchChampion;
        this.matchKills = matchKills;
        this.matchDeaths = matchDeaths;
        this.matchAssists = matchAssists;
        this.kdaRank = kdaRank;
        this.totalDamageDealtToChampionsRank = totalDamageDealtToChampionsRank;
        this.totalDamageTakenRank = totalDamageTakenRank;
        this.visionScoreRank = visionScoreRank;
        this.damageDealtToTurretsRank = damageDealtToTurretsRank;
        this.totalUnitsHealedRank = totalUnitsHealedRank;
        this.goldEarnedRank = goldEarnedRank;
        this.champLevelRank = champLevelRank;
        this.damageDealtToObjectivesRank = damageDealtToObjectivesRank;
        this.neutralMinionsKilledRank = neutralMinionsKilledRank;
        this.magicDamageDealtToChampionsRank = magicDamageDealtToChampionsRank;
        this.wardsKilledRank = wardsKilledRank;
        this.damageSelfMitigatedRank = damageSelfMitigatedRank;
        this.largestCriticalStrikeRank = largestCriticalStrikeRank;
        this.nodeNeutralizeRank = nodeNeutralizeRank;
        this.totalTimeCrowdControlDealtRank = totalTimeCrowdControlDealtRank;
        this.wardsPlacedRank = wardsPlacedRank;
        this.totalDamageDealtRank = totalDamageDealtRank;
        this.timeCCingOthersRank = timeCCingOthersRank;
        this.magicalDamageTakenRank = magicalDamageTakenRank;
        this.physicalDamageDealtToChampionsRank = physicalDamageDealtToChampionsRank;
        this.neutralMinionsKilledTeamJungleRank = neutralMinionsKilledTeamJungleRank;
        this.totalMinionsKilledRank = totalMinionsKilledRank;
        this.visionWardsBoughtInGameRank = visionWardsBoughtInGameRank;
        this.trueDamageTakenRank = trueDamageTakenRank;
        this.totalHealRank = totalHealRank;
        this.longestTimeSpentLivingRank = longestTimeSpentLivingRank;
        this.killingSpreesRank = killingSpreesRank;
        this.neutralMinionsKilledEnemyJungleRank = neutralMinionsKilledEnemyJungleRank;
        this.trueDamageDealtRank = trueDamageDealtRank;
    }
}
