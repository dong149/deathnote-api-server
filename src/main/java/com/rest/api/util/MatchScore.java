package com.rest.api.util;


import com.rest.api.dto.*;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.entity.StatInfo;
import com.rest.api.entity.StatRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchScore {
    /**
     * 매치에 대한 정보를 바탕으로 하여 트롤러 점수를 계산합니다.
     **/


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int compareRank(List<StatRank> statRankList, int participantId) {

        for (int i = 0; i < statRankList.size(); i++) {
            if (statRankList.get(i).getParticipantId() == participantId)
                return statRankList.get(i).getRank();
        }
        return 0;
    }


    public SummonerMatchDto getMatchScore(MatchDto match, String accountId) {
        SummonerMatchDto summonerMatchDto = new SummonerMatchDto();
        List<StatInfo> deathNoteStat = new ArrayList<StatInfo>();

        List<StatInfo> deal = new ArrayList<StatInfo>();
        List<StatInfo> tank = new ArrayList<StatInfo>();
        List<StatInfo> vision = new ArrayList<StatInfo>();
        List<StatInfo> towerDeal = new ArrayList<StatInfo>();
        List<StatInfo> kdaScore = new ArrayList<StatInfo>();


        List<StatRank> dealRank = new ArrayList<StatRank>();
        List<StatRank> tankRank = new ArrayList<StatRank>();
        List<StatRank> visionRank = new ArrayList<StatRank>();
        List<StatRank> towerDealRank = new ArrayList<StatRank>();
        List<StatRank> kdaScoreRank = new ArrayList<StatRank>();

        int sum = 0;
        int deathNoteRank = 0;

        // accountId를 통해 비교하여, participandId값을 알아낸다.
        int mainParticipantId = 0;
        for (ParticipantIdentityDto temp : match.getParticipantIdentities()) {
            if (temp.getPlayer().getAccountId().equals(accountId)) {
                mainParticipantId = temp.getParticipantId();

            }
        }


        for (int i = 0; i < 10; i++) {

            ParticipantStatDto participantStatDto = match.getParticipants().get(i).getStats();
            ParticipantDto participantDto = match.getParticipants().get(i);
            int participantId = participantDto.getParticipantId();

            // kda score를 계산합니다.
            int kills = participantStatDto.getKills();
            int assists = participantStatDto.getAssists();
            int deaths = participantStatDto.getDeaths();
            int kda = kills * 3 + assists * 2 - deaths * 2;


            deal.add(new StatInfo(participantId, "deal", participantStatDto.getTotalDamageDealtToChampions()));
            tank.add(new StatInfo(participantId, "tank", participantStatDto.getTotalDamageTaken()));
            vision.add(new StatInfo(participantId, "vision", participantStatDto.getVisionScore()));
            towerDeal.add(new StatInfo(participantId, "towerDeal", participantStatDto.getDamageDealtToTurrets()));
            kdaScore.add(new StatInfo(participantId, "kdaScore", kda));

        }
        Collections.sort(deal, new ParticipantsComparator());
        Collections.sort(tank, new ParticipantsComparator());
        Collections.sort(vision, new ParticipantsComparator());
        Collections.sort(towerDeal, new ParticipantsComparator());
        Collections.sort(kdaScore, new ParticipantsComparator());


        for (int j = 1; j <= 10; j++) {
            for (int i = 0; i < 10; i++) {
                if (deal.get(i).getParticipantId() == j)
                    dealRank.add(new StatRank(j, 10 - i));
                if (tank.get(i).getParticipantId() == j)
                    tankRank.add(new StatRank(j, 10 - i));
                if (vision.get(i).getParticipantId() == j)
                    visionRank.add(new StatRank(j, 10 - i));
                if (towerDeal.get(i).getParticipantId() == j)
                    towerDealRank.add(new StatRank(j, 10 - i));
                if (kdaScore.get(i).getParticipantId() == j)
                    kdaScoreRank.add(new StatRank(j, 10 - i));

            }
        }


        for (int i = 1; i <= 10; i++) {
            sum = 0;
            sum = (11 - compareRank(dealRank, i)) * 3 + (11 - compareRank(tankRank, i)) * 1 + (11 - compareRank(visionRank, i)) * 1 + (11 - compareRank(towerDealRank, i)) * 2 + (11 - compareRank(kdaScoreRank, i)) * 3;
            deathNoteStat.add(new StatInfo(i, "deathNoteScore", sum));
        }

        Collections.sort(deathNoteStat, new ParticipantsComparator());
        for (int i = 0; i < 10; i++) {
            if (deathNoteStat.get(i).getParticipantId() == mainParticipantId) {
                deathNoteRank = 10 - i;
                break;
            }
            if(dealRank.get(i).getParticipantId() == mainParticipantId){
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
            }
            if(dealRank.get(i).getParticipantId() == mainParticipantId){
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
            }
        }
        // 각각의 StatRankList 에 대하여, mainParticipantId와 동일한 유저의 랭크를 Set합니다.
        for (int i = 0; i < 10; i++) {
            if(dealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if(tankRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchTankRank(tankRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if(kdaScoreRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchKdaScoreRank(kdaScoreRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < 10; i++) {
            if(towerDealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchTowerDealRank(towerDealRank.get(i).getRank());
                break;
            }
        }


        // 결과 생성 SummonerMatchDto
        summonerMatchDto.setMatchRank(deathNoteRank);
        summonerMatchDto.setMatchChampion(match.getParticipants().get(mainParticipantId-1).getChampionId());
        summonerMatchDto.setMatchWin(match.getParticipants().get(mainParticipantId-1).getStats().isWin());
        summonerMatchDto.setMatchKills(match.getParticipants().get(mainParticipantId-1).getStats().getKills());
        summonerMatchDto.setMatchAssists(match.getParticipants().get(mainParticipantId-1).getStats().getAssists());
        summonerMatchDto.setMatchDeaths(match.getParticipants().get(mainParticipantId-1).getStats().getDeaths());




        return summonerMatchDto;
    }

}



