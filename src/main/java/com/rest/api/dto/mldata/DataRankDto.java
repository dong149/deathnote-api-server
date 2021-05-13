package com.rest.api.dto.mldata;


import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataRankDto {

    int dealRank;
    int tankRank;
    int visionRank;
    int towerDealRank;
    int kdaRank;

    // 시범적으로 넣은 데이터
    int totalUnitsHealedRank;
    int goldEarnedRank;
    int champLevelRank;
    int damageDealtToObjectivesRank;
    int neutralMinionsKilledRank;
    int magicDamageDealtToChampionsRank;
    int wardsKilledRank;
    int damageSelfMitigatedRank;
    int largestCriticalStrikeRank;
    int nodeNeutralizeRank;
    int totalTimeCrowdControlDealtRank;
    int wardsPlacedRank;
    int totalDamageDealtRank;
    int timeCCingOthersRank;
    int magicalDamageTakenRank;
    int physicalDamageDealtToChampionsRank;
    int neutralMinionsKilledTeamJungleRank;
    int totalMinionsKilledRank;
    int visionWardsBoughtInGameRank;
    int trueDamageTakenRank;
    int goldSpentRank;
    int totalHealRank;
    int longestTimeSpentLivingRank;
    int killingSpreesRank;
    int sightWardsBoughtInGameRank;
    int neutralMinionsKilledEnemyJungleRank;
    int trueDamageDealtRank;

    int championId;
    String lane;
    int isWin;




}
