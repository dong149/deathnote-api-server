package com.rest.api.service.deathnote;

import com.rest.api.dto.*;
import com.rest.api.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.dto.StatInfoDto;
import com.rest.api.dto.StatRankDto;
import com.rest.api.entity.summoner.Match;
import com.rest.api.entity.summoner.Summoner;
import com.rest.api.enumerator.QueueType;
import com.rest.api.repository.MatchJpaRepo;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.service.riot.RiotService;
import com.rest.api.util.NameFormatter;
import com.rest.api.util.ParticipantsComparator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@RequiredArgsConstructor
@Service
public class DeathnoteService {


    private final RiotService riotService;
    private final SummonerJpaRepo summonerJpaRepo;
    private final MatchJpaRepo matchJpaRepo;
    private static final int TOTAL_SUMMONER = 10;

    private static Logger logger = LoggerFactory.getLogger(DeathnoteService.class);
    final ExecutorService executor = Executors.newFixedThreadPool(20);

    @Value("${lol.current.season}")
    private int CURRENT_SEASON;

    @Value("${lol.game.size}")
    private int GAME_MAX_SIZE;

    public SummonerInfoDto getSummonerInfoDtoWithSummonerName(String name, boolean reload) throws IOException, URISyntaxException {

        int matchCnt = 0;
        int matchScoreSum = 0;
        int matchFinalScore = 0;
        int matchWin = 0;
        int matchLose = 0;
        int matchWinningRate = 0;


        SummonerDto summonerDto = riotService.getSummonerDtoWithRiotAPIBySummonerName(name);


        // TODO: SummonerInfoDto와 매핑 필요
        Optional<Summoner> summonerOptional = summonerJpaRepo.findById(summonerDto.getAccountId());
        if (summonerOptional.isPresent() && !reload) {
            Summoner summonerFromDB = summonerOptional.get();
            List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
            for (Match match : summonerFromDB.getMatches()) {
                // match -> summonerMatchDtoList
                summonerMatchDtoList.add(SummonerMatchDto.builder()
                        .matchAssists(match.getMatchAssists())
                        .matchChampion(match.getMatchChampion())
                        .matchDealRank(match.getMatchDealRank())
                        .matchDeaths(match.getMatchDeaths())
                        .matchKdaScoreRank(match.getMatchKdaScoreRank())
                        .matchKills(match.getMatchKills())
                        .matchRank(match.getMatchRank())
                        .matchTankRank(match.getMatchTankRank())
                        .matchTowerDealRank(match.getMatchTowerDealRank())
                        .matchWin(match.isMatchWin())
                        .build()
                );
            }


            logger.info(new StringBuilder()
                    .append("DB에서 불러오기 성공")
                    .toString()
            );
            return SummonerInfoDto.builder()
                    .summonerName(summonerFromDB.getSummonerName())
                    .accountId(summonerFromDB.getAccountId())
                    .trollerScore(summonerFromDB.getTrollerScore())
                    .summonerTier(summonerFromDB.getSummonerTier())
                    .summonerRank(summonerFromDB.getSummonerRank())
                    .summonerMatch(summonerMatchDtoList)
                    .summonerLevel(summonerFromDB.getSummonerLevel())
                    .summonerIcon(summonerFromDB.getProfileIconId())
                    .matchWinningRate(summonerFromDB.getMatchWinningRate())
                    .matchLose(summonerFromDB.getMatchLose())
                    .matchWin(summonerFromDB.getMatchWin())
                    .matchCount(summonerFromDB.getMatchCount())
                    .build();
        }
        if (reload) {
            matchJpaRepo.deleteAllByMatchAccountId(summonerDto.getAccountId());
            logger.info(new StringBuilder()
                    .append("Match 정보 Reload")
                    .toString()
            );
        }

        MatchListDto matchListDto = riotService.getMatchListDtoWithRiotAPIByEncryptedAccountIdAndQueueAndSeason(summonerDto.getAccountId(), QueueType.SOLO_RANK_QUEUE, CURRENT_SEASON);
        LeagueEntryDto leagueEntryDto = riotService.getLeagueEntryDtoWithRiotAPIByEncryptedId(summonerDto.getId());
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        List<Summoner> summoners = new ArrayList<>();
        String encryptedAccountId = summonerDto.getAccountId();

        List<MatchReferenceDto> matchReferenceDtoList = matchListDto.getMatches();
        List<Long> gameIdList = new ArrayList<>();

        // MatchId를 gameIdList에 저장합니다.
        for (MatchReferenceDto curMatchReferenceDto : matchReferenceDtoList) {
            gameIdList.add(curMatchReferenceDto.getGameId());
            if (gameIdList.size() >= GAME_MAX_SIZE) {
                break;
            }
        }
        matchCnt = gameIdList.size();

        /*
         * TODO: gameIdList의 값들을 각각 API에 요청해야한다. 요청할 때, 동시에 여러번 호출하게끔 만들어야된다. 여기서 시간이 가장 많이 오바됨.
         */
        SummonerMatchDto summonerMatchDto;

        final List<Future<SummonerMatchDto>> futures = new ArrayList<>();

        for (Long gameId : gameIdList) {
            Callable<SummonerMatchDto> callable = new Callable<SummonerMatchDto>() {
                @Override
                public SummonerMatchDto call() throws Exception {
                    SummonerMatchDto result = getMatchScore(riotService.getMatchDtoWithRiotAPIByMatchId(gameId), encryptedAccountId);
                    return result;
                }
            };
            futures.add(executor.submit(callable));
        }


        try {
            for (Future<SummonerMatchDto> item : futures) {

                SummonerMatchDto summonerMatchDtoFuture = item.get();


                summonerMatchDtoList.add(summonerMatchDtoFuture);
                matches.add(summonerMatchDtoFuture.toEntity(summonerDto.getAccountId()));


                if (summonerMatchDtoFuture.isMatchWin()) {
                    matchScoreSum += summonerMatchDtoFuture.getMatchRank();
                    matchWin++;
                } else {
                    matchScoreSum += summonerMatchDtoFuture.getMatchRank() - 1;
                    matchLose++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        matchFinalScore = (int) ((11 - ((1.0) * matchScoreSum / matchCnt)) * 10);
        matchWinningRate = (int) ((1.0) * matchWin / (matchWin + matchLose) * 100);

        Summoner summoner = Summoner.builder()
                .summonerName(summonerDto.getName())
                .summonerDecodedName(NameFormatter.getFormattedSummonerName(summonerDto.getName()))
                .summonerRank(leagueEntryDto.getRank())
                .summonerTier(leagueEntryDto.getTier())
                .trollerScore(matchFinalScore)
                .accountId(summonerDto.getAccountId())
                .summonerId(summonerDto.getId())
                .profileIconId(summonerDto.getProfileIconId())
                .summonerLevel(summonerDto.getSummonerLevel())
                .matchCount(matchCnt)
                .matchWin(matchWin)
                .matchLose(matchLose)
                .matchWinningRate(matchWinningRate)
                .matches(matches)
                .build();


        summonerJpaRepo.save(summoner);

        return SummonerInfoDto.builder()
                .trollerScore(matchFinalScore)
                .accountId(summonerDto.getAccountId())
                .summonerTier(leagueEntryDto.getTier())
                .summonerRank(leagueEntryDto.getRank())
                .summonerName(summonerDto.getName())
                .summonerMatch(summonerMatchDtoList)
                .summonerLevel(summonerDto.getSummonerLevel())
                .summonerIcon(summonerDto.getProfileIconId())
                .matchWinningRate(matchWinningRate)
                .matchLose(matchLose)
                .matchWin(matchWin)
                .matchCount(matchCnt)
                .build();


    }


    public static SummonerMatchDto getMatchScore(MatchDto match, String accountId) {
        SummonerMatchDto summonerMatchDto = new SummonerMatchDto();
        List<StatInfoDto> deathNoteStat = new ArrayList<StatInfoDto>();
        List<StatInfoDto> deal = new ArrayList<StatInfoDto>();
        List<StatInfoDto> tank = new ArrayList<StatInfoDto>();
        List<StatInfoDto> vision = new ArrayList<StatInfoDto>();
        List<StatInfoDto> towerDeal = new ArrayList<StatInfoDto>();
        List<StatInfoDto> kdaScore = new ArrayList<StatInfoDto>();
        List<StatRankDto> dealRank = new ArrayList<StatRankDto>();
        List<StatRankDto> tankRank = new ArrayList<StatRankDto>();
        List<StatRankDto> visionRank = new ArrayList<StatRankDto>();
        List<StatRankDto> towerDealRank = new ArrayList<StatRankDto>();
        List<StatRankDto> kdaScoreRank = new ArrayList<StatRankDto>();

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


            deal.add(new StatInfoDto(participantId, "deal", participantStatDto.getTotalDamageDealtToChampions()));
            tank.add(new StatInfoDto(participantId, "tank", participantStatDto.getTotalDamageTaken()));
            vision.add(new StatInfoDto(participantId, "vision", participantStatDto.getVisionScore()));
            towerDeal.add(new StatInfoDto(participantId, "towerDeal", participantStatDto.getDamageDealtToTurrets()));
            kdaScore.add(new StatInfoDto(participantId, "kdaScore", kda));

        }
        Collections.sort(deal, new ParticipantsComparator());
        Collections.sort(tank, new ParticipantsComparator());
        Collections.sort(vision, new ParticipantsComparator());
        Collections.sort(towerDeal, new ParticipantsComparator());
        Collections.sort(kdaScore, new ParticipantsComparator());


        for (int j = 1; j <= TOTAL_SUMMONER; j++) {
            for (int i = 0; i < TOTAL_SUMMONER; i++) {
                if (deal.get(i).getParticipantId() == j)
                    dealRank.add(new StatRankDto(j, 10 - i));
                if (tank.get(i).getParticipantId() == j)
                    tankRank.add(new StatRankDto(j, 10 - i));
                if (vision.get(i).getParticipantId() == j)
                    visionRank.add(new StatRankDto(j, 10 - i));
                if (towerDeal.get(i).getParticipantId() == j)
                    towerDealRank.add(new StatRankDto(j, 10 - i));
                if (kdaScore.get(i).getParticipantId() == j)
                    kdaScoreRank.add(new StatRankDto(j, 10 - i));

            }
        }


        for (int i = 1; i <= TOTAL_SUMMONER; i++) {
            sum = 0;
            sum = (11 - compareRank(dealRank, i)) * 3 + (11 - compareRank(tankRank, i)) * 1 + (11 - compareRank(visionRank, i)) * 1 + (11 - compareRank(towerDealRank, i)) * 2 + (11 - compareRank(kdaScoreRank, i)) * 3;
            deathNoteStat.add(new StatInfoDto(i, "deathNoteScore", sum));
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

    Pageable pageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "updatedAt"));
    public SummonerKeywordResponseDto getSummonerNameWithKeyword(String keyword){
        String formattedKeyword = NameFormatter.getFormattedSummonerName(keyword);
        if(formattedKeyword.equals("")){
            return null;
        }
        List<Summoner> summonerList = summonerJpaRepo.search(formattedKeyword,pageable);
        List<SummonerKeywordDto> summonerKeywordDtoList = new ArrayList<>();
        for(Summoner summoner :summonerList){
            summonerKeywordDtoList.add(SummonerKeywordDto
                    .builder()
                    .summonerIcon(summoner.getProfileIconId())
                    .summonerLevel(summoner.getSummonerLevel())
                    .summonerName(summoner.getSummonerName())
                    .SummonerRank(summoner.getSummonerRank())
                    .SummonerTier(summoner.getSummonerTier())
                    .build()
            );
        }

        return SummonerKeywordResponseDto.builder().summonerKeywordDtoList(summonerKeywordDtoList).build();
    }




    private static int compareRank(List<StatRankDto> statRankDtoList, int participantId) {

        for (int i = 0; i < statRankDtoList.size(); i++) {
            if (statRankDtoList.get(i).getParticipantId() == participantId)
                return statRankDtoList.get(i).getRank();
        }
        return 0;
    }



}
