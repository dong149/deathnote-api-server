package com.rest.api.service.v1.deathnote;

public class DeathnoteServiceData {

    public static Feature[] features = new Feature[]{

        // 따로 계산해줌.
        new Feature("kda", 1767),

        // From Riot API
        new Feature("totalDamageDealtToChampions", 1165),
        new Feature("totalDamageTaken", 1151),
        new Feature("visionScore", 1020),
        new Feature("damageDealtToTurrets", 1611),
        new Feature("totalUnitsHealed", 1186),
        new Feature("goldEarned", 1026),
        new Feature("champLevel", 1664),
        new Feature("damageDealtToObjectives", 1268),
        new Feature("neutralMinionsKilled", 1411),
        new Feature("magicDamageDealtToChampions", 1361),
        new Feature("wardsKilled", 1352),
        new Feature("damageSelfMitigated", 1269),
        new Feature("largestCriticalStrike", 1159),
        new Feature("nodeNeutralize", 1365),
        new Feature("totalTimeCrowdControlDealt", 1304),
        new Feature("wardsPlaced", 1102),
        new Feature("totalDamageDealt", 940),
        new Feature("timeCCingOthers", 1232),
        new Feature("magicalDamageTaken", 1361),
        new Feature("physicalDamageDealtToChampions", 1087),
        new Feature("neutralMinionsKilledTeamJungle", 1411),
        new Feature("totalMinionsKilled", 1123),
        new Feature("visionWardsBoughtInGame", 1111),
        new Feature("trueDamageTaken", 1390),
        new Feature("totalHeal", 1279),
        new Feature("longestTimeSpentLiving", 1653),
        new Feature("killingSprees", 1411),
        new Feature("neutralMinionsKilledEnemyJungle", 1411),
        new Feature("trueDamageDealt", 1390),
    };
}
