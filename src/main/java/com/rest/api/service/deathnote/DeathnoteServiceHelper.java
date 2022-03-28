package com.rest.api.service.deathnote;

import com.rest.api.dto.MatchDto;
import com.rest.api.dto.ParticipantIdentityDto;
import com.rest.api.dto.ParticipantStatDto;
import com.rest.api.dto.StatInfoDto;
import com.rest.api.dto.StatRankDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.util.ParticipantsComparator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathnoteServiceHelper {

    /**
     * @param match - 한 게임의 정보
     * @param accountId - Summoner 고유 ID
     * @return SummonerMatchDto - 한 게임 정보 ( 서비스에 맞게 정제함 )
     */

    // DeathnoteScore을 얻기 위해 필요한 지표
    private static final Feature[] FEATURE_LIST = DeathnoteServiceData.features;
    // 한 게임에서 필요한 소환사 수
    private static final int TOTAL_SUMMONER = 10;

    public static SummonerMatchDto getMatchScore(MatchDto match, String accountId)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /*
        1. 지표들을 List에 담는다.
        2. mainParticipantId ( accountId )에 해당하는 소환사를 찾는다.
        3. 지표마다 TOTAL_SUMMONER ( 10 ) 개씩 정보를 넣는다.
        4. 해당 게임의 지표를 Return 한다.
         */

        SummonerMatchDto summonerMatchDto = new SummonerMatchDto();

        int sum = 0;
        int deathNoteRank = 0;

        // accountId를 통해 비교하여, participandId값을 알아낸다.
        int mainParticipantId = findMainParticipantId(match, accountId);

        Map<String, List<StatInfoDto>> statInfoDtoListMap = new HashMap<>();
        Map<String, Integer> summonerRankMap = new HashMap<>();
        // 각 Feature 들의 List 생성
        for (Feature feature : FEATURE_LIST) {
            statInfoDtoListMap.put(feature.getName(), new ArrayList<>());
        }

        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            ParticipantStatDto participantStatDto = match.getParticipants().get(i).getStats();
            int participantId = match.getParticipants().get(i).getParticipantId();

            // kda score를 계산합니다.
            int kills = participantStatDto.getKills();
            int assists = participantStatDto.getAssists();
            int deaths = participantStatDto.getDeaths();
            int kda = kills * 3 + assists * 2 - deaths * 2;

            for (String key : statInfoDtoListMap.keySet()) {

                // kda일 때만 따로 넣어줍니다.
                if (key.equals("kda")) {
                    statInfoDtoListMap.get(key).add(new StatInfoDto(participantId, key, kda));
                    continue;
                }
                String getter = "get" + key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
                Method getterMethod = ParticipantStatDto.class.getMethod(getter);
                statInfoDtoListMap.get(key).add(new StatInfoDto(participantId, key,
                                                                ((Number) getterMethod.invoke(
                                                                    participantStatDto)).longValue()));
            }
        }

        // List<StatInfoDto>를 정렬해줍니다.
        Comparator<StatInfoDto> comparator = new ParticipantsComparator();
        for (Feature feature : FEATURE_LIST) {
            statInfoDtoListMap.get(feature.getName()).sort(comparator);
        }

        List<StatInfoDto> deathnoteStatList = new ArrayList<>();

        int[] deathnoteStatArr = new int[TOTAL_SUMMONER + 1];

        for (Feature feature : FEATURE_LIST) {
            List<StatInfoDto> statInfoDtoList = statInfoDtoListMap.get(feature.getName());
            for (int i = 0; i < TOTAL_SUMMONER; i++) {
                int curParticipantId = statInfoDtoList.get(i).getParticipantId();
                deathnoteStatArr[curParticipantId] += (i + 1) * feature.getWeight();
                if (curParticipantId == mainParticipantId) {
                    summonerRankMap.put(feature.getName(), 10 - i);
                }
            }
        }

        for (int i = 1; i <= TOTAL_SUMMONER; i++) {
            deathnoteStatList.add(new StatInfoDto(i, "deathnoteStat", deathnoteStatArr[i]));
        }

        deathnoteStatList.sort(comparator);

        //TODO : Rank 뿐만 아니라, 점수까지 같이 넣으면 좋을듯. 알고리즘 개선 필요 , 가중치 넣어주면 된다.
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (deathnoteStatList.get(i).getParticipantId() == mainParticipantId) {
                deathNoteRank = 10 - i;
                break;
            }
        }

        return SummonerMatchDto.builder()
                               .matchRank(deathNoteRank)
                               .matchChampion(
                                   match.getParticipants().get(mainParticipantId - 1).getChampionId())
                               .matchWin(
                                   match.getParticipants().get(mainParticipantId - 1).getStats().isWin())
                               .matchKills(
                                   match.getParticipants().get(mainParticipantId - 1).getStats().getKills())
                               .matchDeaths(
                                   match.getParticipants().get(mainParticipantId - 1).getStats().getDeaths())
                               .matchAssists(
                                   match.getParticipants().get(mainParticipantId - 1).getStats().getAssists())
                               .kdaRank(summonerRankMap.get("kda"))
                               .totalDamageDealtToChampionsRank(
                                   summonerRankMap.get("totalDamageDealtToChampions"))
                               .totalDamageTakenRank(summonerRankMap.get("totalDamageTaken"))
                               .visionScoreRank(summonerRankMap.get("visionScore"))
                               .damageDealtToTurretsRank(summonerRankMap.get("damageDealtToTurrets"))
                               .totalUnitsHealedRank(summonerRankMap.get("totalUnitsHealed"))
                               .goldEarnedRank(summonerRankMap.get("goldEarned"))
                               .champLevelRank(summonerRankMap.get("champLevel"))
                               .damageDealtToObjectivesRank(summonerRankMap.get("damageDealtToObjectives"))
                               .neutralMinionsKilledRank(summonerRankMap.get("neutralMinionsKilled"))
                               .magicDamageDealtToChampionsRank(
                                   summonerRankMap.get("magicDamageDealtToChampions"))
                               .wardsKilledRank(summonerRankMap.get("wardsKilled"))
                               .damageSelfMitigatedRank(summonerRankMap.get("damageSelfMitigated"))
                               .largestCriticalStrikeRank(summonerRankMap.get("largestCriticalStrike"))
                               .nodeNeutralizeRank(summonerRankMap.get("nodeNeutralize"))
                               .totalTimeCrowdControlDealtRank(
                                   summonerRankMap.get("totalTimeCrowdControlDealt"))
                               .wardsPlacedRank(summonerRankMap.get("wardsPlaced"))
                               .totalDamageDealtRank(summonerRankMap.get("totalDamageDealt"))
                               .timeCCingOthersRank(summonerRankMap.get("timeCCingOthers"))
                               .magicalDamageTakenRank(summonerRankMap.get("magicalDamageTaken"))
                               .physicalDamageDealtToChampionsRank(
                                   summonerRankMap.get("physicalDamageDealtToChampions"))
                               .neutralMinionsKilledTeamJungleRank(
                                   summonerRankMap.get("neutralMinionsKilledTeamJungle"))
                               .totalMinionsKilledRank(summonerRankMap.get("totalMinionsKilled"))
                               .visionWardsBoughtInGameRank(summonerRankMap.get("visionWardsBoughtInGame"))
                               .trueDamageTakenRank(summonerRankMap.get("trueDamageTaken"))
                               .totalHealRank(summonerRankMap.get("totalHeal"))
                               .longestTimeSpentLivingRank(summonerRankMap.get("longestTimeSpentLiving"))
                               .killingSpreesRank(summonerRankMap.get("killingSprees"))
                               .neutralMinionsKilledEnemyJungleRank(
                                   summonerRankMap.get("neutralMinionsKilledEnemyJungle"))
                               .trueDamageDealtRank(summonerRankMap.get("trueDamageDealt"))
                               .build();
    }

    private static int findMainParticipantId(MatchDto match, String accountId) {
        int mainParticipantId = 0;
        for (ParticipantIdentityDto temp : match.getParticipantIdentities()) {
            if (temp.getPlayer().getAccountId().equals(accountId)) {
                mainParticipantId = temp.getParticipantId();
            }
        }
        return mainParticipantId;
    }

    private static int compareRank(List<StatRankDto> statRankDtoList, int participantId) {

        for (int i = 0; i < statRankDtoList.size(); i++) {
            if (statRankDtoList.get(i).getParticipantId() == participantId) {
                return statRankDtoList.get(i).getRank();
            }
        }
        return 0;
    }
}
