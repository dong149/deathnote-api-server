package com.rest.api.entity.summoner;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column
    private String matchAccountId;

    @Column
    private Integer matchRank;

    @Column
    private Boolean matchWin;

    @Column
    private Integer matchChampion;

    @Column
    private Integer matchKills;

    @Column
    private Integer matchDeaths;

    @Column
    private Integer matchAssists;

    @Column
    private Integer kdaRank;

    @Column
    private Integer totalDamageDealtToChampionsRank;

    @Column
    private Integer totalDamageTakenRank;

    @Column
    private Integer visionScoreRank;

    @Column
    private Integer damageDealtToTurretsRank;

    @Column
    private Integer totalUnitsHealedRank;

    @Column
    private Integer goldEarnedRank;

    @Column
    private Integer champLevelRank;

    @Column
    private Integer damageDealtToObjectivesRank;

    @Column
    private Integer neutralMinionsKilledRank;

    @Column
    private Integer magicDamageDealtToChampionsRank;

    @Column
    private Integer wardsKilledRank;

    @Column
    private Integer damageSelfMitigatedRank;

    @Column
    private Integer largestCriticalStrikeRank;

    @Column
    private Integer nodeNeutralizeRank;

    @Column
    private Integer totalTimeCrowdControlDealtRank;

    @Column
    private Integer wardsPlacedRank;

    @Column
    private Integer totalDamageDealtRank;

    @Column
    private Integer timeCCingOthersRank;

    @Column
    private Integer magicalDamageTakenRank;

    @Column
    private Integer physicalDamageDealtToChampionsRank;

    @Column
    private Integer neutralMinionsKilledTeamJungleRank;

    @Column
    private Integer totalMinionsKilledRank;

    @Column
    private Integer visionWardsBoughtInGameRank;

    @Column
    private Integer trueDamageTakenRank;

    @Column
    private Integer totalHealRank;

    @Column
    private Integer longestTimeSpentLivingRank;

    @Column
    private Integer killingSpreesRank;

    @Column
    private Integer neutralMinionsKilledEnemyJungleRank;

    @Column
    private Integer trueDamageDealtRank;

    @CreationTimestamp
    private LocalDateTime createdAt;

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
