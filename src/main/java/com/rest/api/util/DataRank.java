package com.rest.api.util;

import com.rest.api.dto.MatchDto;
import com.rest.api.dto.ParticipantDto;
import com.rest.api.dto.ParticipantStatDto;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.entity.StatInfo;

import java.util.ArrayList;
import java.util.List;

public class DataRank {


    // 한 명의 데이터를 리턴한다.
    // 이 때, DataRankDto의 형태로 리턴한다. 한 명의 각각의 데이터에 대한 Rank 정보와 승/패 정보가 담겨있다.
    public List<DataRankDto> getDataRank(MatchDto match) {


        SummonerMatchDto summonerMatchDto = new SummonerMatchDto();
        List<StatInfo> deathNoteStat = new ArrayList<>();
        List<StatInfo> deal = new ArrayList<>();
        List<StatInfo> tank = new ArrayList<>();
        List<StatInfo> vision = new ArrayList<>();
        List<StatInfo> towerDeal = new ArrayList<>();
        List<StatInfo> kda = new ArrayList<>();


        // 시범적으로 넣어보는 값들
        List<StatInfo> totalUnitsHealed = new ArrayList<>();
        List<StatInfo> goldEarned = new ArrayList<>();
        List<StatInfo> champLevel = new ArrayList<>();
        List<StatInfo> damageDealtToObjectives = new ArrayList<>();
        List<StatInfo> neutralMinionsKilled = new ArrayList<>();
        List<StatInfo> magicDamageDealtToChampions = new ArrayList<>();
        List<StatInfo> wardsKilled = new ArrayList<>();
        List<StatInfo> damageSelfMitigated = new ArrayList<>();
        List<StatInfo> largestCriticalStrike = new ArrayList<>();
        List<StatInfo> nodeNeutralize = new ArrayList<>();
        List<StatInfo> totalTimeCrowdControlDealt = new ArrayList<>();
        List<StatInfo> wardsPlaced = new ArrayList<>();
        List<StatInfo> totalDamageDealt = new ArrayList<>();
        List<StatInfo> timeCCingOthers = new ArrayList<>();
        List<StatInfo> magicalDamageTaken = new ArrayList<>();
        List<StatInfo> physicalDamageDealtToChampions = new ArrayList<>();
        List<StatInfo> neutralMinionsKilledTeamJungle = new ArrayList<>();
        List<StatInfo> totalMinionsKilled = new ArrayList<>();
        List<StatInfo> visionWardsBoughtInGame = new ArrayList<>();
        List<StatInfo> trueDamageTaken = new ArrayList<>();
        List<StatInfo> goldSpent = new ArrayList<>();
        List<StatInfo> totalHeal = new ArrayList<>();
        List<StatInfo> longestTimeSpentLiving = new ArrayList<>();
        List<StatInfo> killingSprees = new ArrayList<>();
        List<StatInfo> sightWardsBoughtInGame = new ArrayList<>();
        List<StatInfo> neutralMinionsKilledEnemyJungle = new ArrayList<>();
        List<StatInfo> trueDamageDealt = new ArrayList<>();


        // 핵심 지표
        int[] dealRank = new int[11];
        int[] tankRank = new int[11];
        int[] visionRank = new int[11];
        int[] towerDealRank = new int[11];
        int[] kdaRank = new int[11];

        // 시범적으로 넣어보는 값들
        int[] totalUnitsHealedRank = new int[11];
        int[] goldEarnedRank = new int[11];
        int[] champLevelRank = new int[11];
        int[] damageDealtToObjectivesRank = new int[11];
        int[] neutralMinionsKilledRank = new int[11];
        int[] magicDamageDealtToChampionsRank = new int[11];
        int[] wardsKilledRank = new int[11];
        int[] damageSelfMitigatedRank = new int[11];
        int[] largestCriticalStrikeRank = new int[11];
        int[] nodeNeutralizeRank = new int[11];
        int[] totalTimeCrowdControlDealtRank = new int[11];
        int[] wardsPlacedRank = new int[11];
        int[] totalDamageDealtRank = new int[11];
        int[] timeCCingOthersRank = new int[11];
        int[] magicalDamageTakenRank = new int[11];
        int[] physicalDamageDealtToChampionsRank = new int[11];
        int[] neutralMinionsKilledTeamJungleRank = new int[11];
        int[] totalMinionsKilledRank = new int[11];
        int[] visionWardsBoughtInGameRank = new int[11];
        int[] trueDamageTakenRank = new int[11];
        int[] goldSpentRank = new int[11];
        int[] totalHealRank = new int[11];
        int[] longestTimeSpentLivingRank = new int[11];
        int[] killingSpreesRank = new int[11];
        int[] sightWardsBoughtInGameRank = new int[11];
        int[] neutralMinionsKilledEnemyJungleRank = new int[11];
        int[] trueDamageDealtRank = new int[11];


        // 승 패 여부
        int[] championId = new int[11];
        int[] isWin = new int[11];

        // 라인이 어디인지
        String[] lane = new String[11];

        // 각각의 리스트에 데이터를 추가한다.
        for (int i = 0; i < 10; i++) {

            ParticipantStatDto participantStatDto = match.getParticipants().get(i).getStats();
            ParticipantDto participantDto = match.getParticipants().get(i);
            int participantId = participantDto.getParticipantId();

            // kda score를 계산합니다.
            int kills = participantStatDto.getKills();
            int assists = participantStatDto.getAssists();
            int deaths = participantStatDto.getDeaths();
            int kdaScore = kills * 3 + assists * 2 - deaths * 2;

            deal.add(new StatInfo(participantId, participantStatDto.getTotalDamageDealtToChampions()));
            tank.add(new StatInfo(participantId, participantStatDto.getTotalDamageTaken()));
            vision.add(new StatInfo(participantId, participantStatDto.getVisionScore()));
            towerDeal.add(new StatInfo(participantId, participantStatDto.getDamageDealtToTurrets()));
            kda.add(new StatInfo(participantId, kdaScore));


            //시범적으로 넣어보는 값들
            totalUnitsHealed.add(new StatInfo(participantId,participantStatDto.getTotalUnitsHealed()));
            goldEarned.add(new StatInfo(participantId,participantStatDto.getGoldEarned()));
            champLevel.add(new StatInfo(participantId,participantStatDto.getChampLevel()));
            damageDealtToObjectives.add(new StatInfo(participantId,participantStatDto.getDamageDealtToObjectives()));
            neutralMinionsKilled.add(new StatInfo(participantId,participantStatDto.getNeutralMinionsKilled()));
            magicDamageDealtToChampions.add(new StatInfo(participantId,participantStatDto.getMagicDamageDealtToChampions()));
            wardsKilled.add(new StatInfo(participantId,participantStatDto.getWardsKilled()));
            damageSelfMitigated.add(new StatInfo(participantId,participantStatDto.getDamageSelfMitigated()));
            largestCriticalStrike.add(new StatInfo(participantId,participantStatDto.getLargestCriticalStrike()));
            nodeNeutralize.add(new StatInfo(participantId,participantStatDto.getNodeNeutralize()));
            totalTimeCrowdControlDealt.add(new StatInfo(participantId,participantStatDto.getTotalTimeCrowdControlDealt()));
            wardsPlaced.add(new StatInfo(participantId,participantStatDto.getWardsPlaced()));
            totalDamageDealt.add(new StatInfo(participantId,participantStatDto.getTotalDamageDealt()));
            timeCCingOthers.add(new StatInfo(participantId,participantStatDto.getTimeCCingOthers()));
            magicalDamageTaken.add(new StatInfo(participantId,participantStatDto.getMagicalDamageTaken()));
            physicalDamageDealtToChampions.add(new StatInfo(participantId,participantStatDto.getPhysicalDamageDealtToChampions()));
            neutralMinionsKilledTeamJungle.add(new StatInfo(participantId,participantStatDto.getNeutralMinionsKilledTeamJungle()));
            totalMinionsKilled.add(new StatInfo(participantId,participantStatDto.getTotalMinionsKilled()));
            visionWardsBoughtInGame.add(new StatInfo(participantId,participantStatDto.getVisionWardsBoughtInGame()));
            trueDamageTaken.add(new StatInfo(participantId,participantStatDto.getTrueDamageTaken()));
            goldSpent.add(new StatInfo(participantId,participantStatDto.getGoldSpent()));
            totalHeal.add(new StatInfo(participantId,participantStatDto.getTotalHeal()));
            longestTimeSpentLiving.add(new StatInfo(participantId,participantStatDto.getLongestTimeSpentLiving()));
            killingSprees.add(new StatInfo(participantId,participantStatDto.getKillingSprees()));
            sightWardsBoughtInGame.add(new StatInfo(participantId,participantStatDto.getSightWardsBoughtInGame()));
            neutralMinionsKilledEnemyJungle.add(new StatInfo(participantId,participantStatDto.getNeutralMinionsKilledEnemyJungle()));
            trueDamageDealt.add(new StatInfo(participantId,participantStatDto.getTrueDamageDealt()));




            championId[participantId] = match.getParticipants().get(i).getChampionId();
            if(participantStatDto.isWin())
                isWin[participantId] = 1;
            else
                isWin[participantId] = 0;
            lane[participantId] = match.getParticipants().get(i).getTimeline().getLane();

        }


        // 오름차순으로 정렬한다.
        deal.sort(new ParticipantsComparator());
        tank.sort(new ParticipantsComparator());
        vision.sort(new ParticipantsComparator());
        towerDeal.sort(new ParticipantsComparator());
        kda.sort(new ParticipantsComparator());


        // 시범적으로 넣어보는 값들
        totalUnitsHealed.sort(new ParticipantsComparator());
        totalUnitsHealed.sort(new ParticipantsComparator());
        goldEarned.sort(new ParticipantsComparator());
        champLevel.sort(new ParticipantsComparator());
        damageDealtToObjectives.sort(new ParticipantsComparator());
        neutralMinionsKilled.sort(new ParticipantsComparator());
        magicDamageDealtToChampions.sort(new ParticipantsComparator());
        wardsKilled.sort(new ParticipantsComparator());
        damageSelfMitigated.sort(new ParticipantsComparator());
        largestCriticalStrike.sort(new ParticipantsComparator());
        nodeNeutralize.sort(new ParticipantsComparator());
        totalTimeCrowdControlDealt.sort(new ParticipantsComparator());
        wardsPlaced.sort(new ParticipantsComparator());
        totalDamageDealt.sort(new ParticipantsComparator());
        timeCCingOthers.sort(new ParticipantsComparator());
        magicalDamageTaken.sort(new ParticipantsComparator());
        physicalDamageDealtToChampions.sort(new ParticipantsComparator());
        neutralMinionsKilledTeamJungle.sort(new ParticipantsComparator());
        totalMinionsKilled.sort(new ParticipantsComparator());
        visionWardsBoughtInGame.sort(new ParticipantsComparator());
        trueDamageTaken.sort(new ParticipantsComparator());
        goldSpent.sort(new ParticipantsComparator());
        totalHeal.sort(new ParticipantsComparator());
        longestTimeSpentLiving.sort(new ParticipantsComparator());
        killingSprees.sort(new ParticipantsComparator());
        sightWardsBoughtInGame.sort(new ParticipantsComparator());
        neutralMinionsKilledEnemyJungle.sort(new ParticipantsComparator());
        trueDamageDealt.sort(new ParticipantsComparator());

        for (int i = 0; i < 10; i++) {
            int rank = 10 - i;
            dealRank[getParticipantId(deal, i)] = rank;
            tankRank[getParticipantId(tank, i)] = rank;
            visionRank[getParticipantId(vision, i)] = rank;
            towerDealRank[getParticipantId(towerDeal, i)] = rank;
            kdaRank[getParticipantId(kda, i)] = rank;

            //시범적으로 넣어보는 값들
            totalUnitsHealedRank[getParticipantId(totalUnitsHealed,i)] = rank;
            goldEarnedRank[getParticipantId(goldEarned,i)] = rank;
            champLevelRank[getParticipantId(champLevel,i)] = rank;
            damageDealtToObjectivesRank[getParticipantId(damageDealtToObjectives,i)] = rank;
            neutralMinionsKilledRank[getParticipantId(neutralMinionsKilled,i)] = rank;
            magicDamageDealtToChampionsRank[getParticipantId(magicDamageDealtToChampions,i)] = rank;
            wardsKilledRank[getParticipantId(wardsKilled,i)] = rank;
            damageSelfMitigatedRank[getParticipantId(damageSelfMitigated,i)] = rank;
            largestCriticalStrikeRank[getParticipantId(largestCriticalStrike,i)] = rank;
            nodeNeutralizeRank[getParticipantId(nodeNeutralize,i)] = rank;
            totalTimeCrowdControlDealtRank[getParticipantId(totalTimeCrowdControlDealt,i)] = rank;
            wardsPlacedRank[getParticipantId(wardsPlaced,i)] = rank;
            totalDamageDealtRank[getParticipantId(totalDamageDealt,i)] = rank;
            timeCCingOthersRank[getParticipantId(timeCCingOthers,i)] = rank;
            magicalDamageTakenRank[getParticipantId(magicalDamageTaken,i)] = rank;
            physicalDamageDealtToChampionsRank[getParticipantId(physicalDamageDealtToChampions,i)] = rank;
            neutralMinionsKilledTeamJungleRank[getParticipantId(neutralMinionsKilledTeamJungle,i)] = rank;
            totalMinionsKilledRank[getParticipantId(totalMinionsKilled,i)] = rank;
            visionWardsBoughtInGameRank[getParticipantId(visionWardsBoughtInGame,i)] = rank;
            trueDamageTakenRank[getParticipantId(trueDamageTaken,i)] = rank;
            goldSpentRank[getParticipantId(goldSpent,i)] = rank;
            totalHealRank[getParticipantId(totalHeal,i)] = rank;
            longestTimeSpentLivingRank[getParticipantId(longestTimeSpentLiving,i)] = rank;
            killingSpreesRank[getParticipantId(killingSprees,i)] = rank;
            sightWardsBoughtInGameRank[getParticipantId(sightWardsBoughtInGame,i)] = rank;
            neutralMinionsKilledEnemyJungleRank[getParticipantId(neutralMinionsKilledEnemyJungle,i)] = rank;
            trueDamageDealtRank[getParticipantId(trueDamageDealt,i)] = rank;
        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i < 11; i++) {
//            sb.append(dealRank[i]).append(',');
//            sb.append(tankRank[i]).append(',');
//            sb.append(visionRank[i]).append(',');
//            sb.append(towerDealRank[i]).append(',');
//            sb.append(kdaRank[i]).append('\n');
//        }
//        System.out.print(sb);


        List<DataRankDto> dataRankDtos = new ArrayList<>();
        for(int i=1;i<11;i++){
            dataRankDtos.add(new DataRankDto(dealRank[i],tankRank[i],visionRank[i],towerDealRank[i],kdaRank[i],totalUnitsHealedRank[i],goldEarnedRank[i],champLevelRank[i],damageDealtToObjectivesRank[i],neutralMinionsKilledRank[i],magicDamageDealtToChampionsRank[i],wardsKilledRank[i],damageSelfMitigatedRank[i],largestCriticalStrikeRank[i],nodeNeutralizeRank[i],totalTimeCrowdControlDealtRank[i],wardsPlacedRank[i],totalDamageDealtRank[i],timeCCingOthersRank[i],magicalDamageTakenRank[i],physicalDamageDealtToChampionsRank[i],neutralMinionsKilledTeamJungleRank[i],totalMinionsKilledRank[i],visionWardsBoughtInGameRank[i],trueDamageTakenRank[i],goldSpentRank[i],totalHealRank[i],longestTimeSpentLivingRank[i],killingSpreesRank[i],sightWardsBoughtInGameRank[i],neutralMinionsKilledEnemyJungleRank[i],trueDamageDealtRank[i],championId[i],lane[i],isWin[i]));
        }
        return dataRankDtos;
    }

    private static int getParticipantId(List<StatInfo> li, int idx) {
        return li.get(idx).getParticipantId();
    }
}
