package com.rest.api.util;

import com.rest.api.model.dto.MatchDto;
import com.rest.api.model.dto.StatInfoDto;
import com.rest.api.model.dto.V5ParticipantDto;
import com.rest.api.model.dto.result.SummonerMatchDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DeathnoteUtils {
    
    /**
     * Match 연산시, api 호출 갯수
     */
    public static int MATCH_MAX_COUNT = 10;
    public static int CURRENT_SEASON = 13;
    public static int SUMMONER_IN_GAME = 10;
    public static int KILLS_WEIGHT = 3;
    public static int ASSISTS_WEIGHT = 3;
    public static int DEATHS_WEIGHT = 3;
    
    // TODO
    public static SummonerMatchDto getMatchScore(MatchDto matchDto) {
        
        Map<String, List<StatInfoDto>> statInfoMap = new HashMap<>();
        Map<String, Integer> summonerRankMap = new HashMap<>();
        
        for (Feature feature : Feature.FEATURES) {
            statInfoMap.put(feature.name, new ArrayList<>());
        }
        
        List<V5ParticipantDto> participantDtos = matchDto.getInfo().getParticipants();
        for (int i = 0; i < SUMMONER_IN_GAME; i++) {
            V5ParticipantDto participantDto = participantDtos.get(i);
            
            int participantId = participantDto.getParticipantId();
        }
        
        return null;
    }
    
    /**
     * KDA 점수를 반환한다.
     *
     * @param participantDto
     * @return KDA Score
     */
    public static int getKdaScore(V5ParticipantDto participantDto) {
        int kills = participantDto.getKills();
        int assists = participantDto.getAssists();
        int deaths = participantDto.getDeaths();
        
        return kills * KILLS_WEIGHT + assists * ASSISTS_WEIGHT - deaths * DEATHS_WEIGHT;
    }
    
    private static class Feature {
        
        private final String name;
        private final int weight;
        
        private Feature(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
        
        private static Feature[] FEATURES =
            new Feature
                []{
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
                new Feature("trueDamageDealt", 1390)};
    }
}
