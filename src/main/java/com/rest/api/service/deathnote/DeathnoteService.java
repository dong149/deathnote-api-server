package com.rest.api.service.deathnote;

import com.rest.api.dto.*;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.entity.StatInfo;
import com.rest.api.entity.StatRank;
import com.rest.api.enumerator.QueueType;
import com.rest.api.service.riot.RiotService;
import com.rest.api.util.ParticipantsComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
public class DeathnoteService {

    private final RiotService riotService;

    private static final int TOTAL_SUMMONER = 10;

    @Value("${lol.current.season}")
    private int CURRENT_SEASON;

    public SummonerInfoDto getSummonerInfoDtoWithSummonerName(String name) throws IOException, URISyntaxException {

        int matchCnt = 0;
        int matchScoreSum = 0;
        int matchFinalScore = 0;
        int matchWin = 0;
        int matchLose = 0;


        SummonerDto summonerDto =  riotService.getSummonerDtoWithRiotAPIBySummonerName(name);
        MatchListDto matchListDto = riotService.getMatchListDtoWithRiotAPIByEncryptedAccountIdAndQueueAndSeason(summonerDto.getAccountId(),QueueType.SOLO_RANK_QUEUE,CURRENT_SEASON);
        LeagueEntryDto leagueEntryDto = riotService.getLeagueEntryDtoWithRiotAPIByEncryptedId(summonerDto.getId());
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        String encryptedAccountId = summonerDto.getAccountId();

        List<MatchReferenceDto> matchReferenceDtoList = matchListDto.getMatches();
        List<Long> gameIdList = new ArrayList<>();

        // MatchId를 gameIdList에 저장합니다.
        for (MatchReferenceDto curMatchReferenceDto : matchReferenceDtoList) {
            gameIdList.add(curMatchReferenceDto.getGameId());
            if (gameIdList.size() >= 20) {
                break;
            }
        }
        matchCnt = gameIdList.size();

        /*
        * TODO: gameIdList의 값들을 각각 API에 요청해야한다. 요청할 때, 동시에 여러번 호출하게끔 만들어야된다. 여기서 시간이 가장 많이 오바됨.
         */
        SummonerMatchDto summonerMatchDto;
        for(Long gameId : gameIdList){
            MatchDto matchDto = riotService.getMatchDtoWithRiotAPIByMatchId(gameId);
            summonerMatchDto = getMatchScore(matchDto,encryptedAccountId);
            summonerMatchDtoList.add(summonerMatchDto);
            if(summonerMatchDto.isMatchWin()){
                matchScoreSum += summonerMatchDto.getMatchRank();
                matchWin++;
            }else{
                matchScoreSum += summonerMatchDto.getMatchRank()-1;
                matchLose++;
            }
        }

        matchFinalScore = (int) (11 - (matchScoreSum / matchCnt) * 1.0) * 10;

        return SummonerInfoDto.builder()
                .trollerScore(matchFinalScore)
                .summonerTier(leagueEntryDto.getTier())
                .summonerRank(leagueEntryDto.getRank())
                .summonerName(summonerDto.getName())
                .summonerMatch(summonerMatchDtoList)
                .summonerLevel(summonerDto.getSummonerLevel())
                .summonerIcon(summonerDto.getProfileIconId())
                .matchWinningRate((int) ((1.0) * matchWin / (matchWin + matchLose) * 100))
                .matchLose(matchLose)
                .matchWin(matchWin)
                .matchCount(matchCnt)
                .build();

    }





    public static SummonerMatchDto getMatchScore(MatchDto match, String accountId) {
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


        for (int i = 0; i < TOTAL_SUMMONER; i++) {

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


        for (int j = 1; j <= TOTAL_SUMMONER; j++) {
            for (int i = 0; i < TOTAL_SUMMONER; i++) {
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


        for (int i = 1; i <= TOTAL_SUMMONER; i++) {
            sum = 0;
            sum = (11 - compareRank(dealRank, i)) * 3 + (11 - compareRank(tankRank, i)) * 1 + (11 - compareRank(visionRank, i)) * 1 + (11 - compareRank(towerDealRank, i)) * 2 + (11 - compareRank(kdaScoreRank, i)) * 3;
            deathNoteStat.add(new StatInfo(i, "deathNoteScore", sum));
        }

        Collections.sort(deathNoteStat, new ParticipantsComparator());
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (deathNoteStat.get(i).getParticipantId() == mainParticipantId) {
                deathNoteRank = TOTAL_SUMMONER - i;
                break;
            }
            if (dealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
            }
            if (dealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
            }
        }
        // 각각의 StatRankList 에 대하여, mainParticipantId와 동일한 유저의 랭크를 Set합니다.
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (dealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchDealRank(dealRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (tankRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchTankRank(tankRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (kdaScoreRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchKdaScoreRank(kdaScoreRank.get(i).getRank());
                break;
            }
        }
        for (int i = 0; i < TOTAL_SUMMONER; i++) {
            if (towerDealRank.get(i).getParticipantId() == mainParticipantId) {
                summonerMatchDto.setMatchTowerDealRank(towerDealRank.get(i).getRank());
                break;
            }
        }


        // 결과 생성 SummonerMatchDto
        summonerMatchDto.setMatchRank(deathNoteRank);
        summonerMatchDto.setMatchChampion(match.getParticipants().get(mainParticipantId - 1).getChampionId());
        summonerMatchDto.setMatchWin(match.getParticipants().get(mainParticipantId - 1).getStats().isWin());
        summonerMatchDto.setMatchKills(match.getParticipants().get(mainParticipantId - 1).getStats().getKills());
        summonerMatchDto.setMatchAssists(match.getParticipants().get(mainParticipantId - 1).getStats().getAssists());
        summonerMatchDto.setMatchDeaths(match.getParticipants().get(mainParticipantId - 1).getStats().getDeaths());


        return summonerMatchDto;
    }

    private static int compareRank(List<StatRank> statRankList, int participantId) {

        for (int i = 0; i < statRankList.size(); i++) {
            if (statRankList.get(i).getParticipantId() == participantId)
                return statRankList.get(i).getRank();
        }
        return 0;
    }


}
