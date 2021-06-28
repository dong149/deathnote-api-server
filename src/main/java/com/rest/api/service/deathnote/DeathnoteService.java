package com.rest.api.service.deathnote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.config.AppConfig;
import com.rest.api.dto.*;
import com.rest.api.dto.response.rank.TrollerRankerResponseDto;
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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final ModelMapper modelMapper;

    private static Logger logger = LoggerFactory.getLogger(DeathnoteService.class);
    final ExecutorService executor = Executors.newFixedThreadPool(20);

    @Value("${lol.current.season}")
    private int CURRENT_SEASON;

    @Value("${lol.game.size}")
    private int GAME_MAX_SIZE;



    public SummonerInfoDto getSummonerInfoDtoWithSummonerName(String name, boolean reload) {

        int matchCnt = 0;
        int matchScoreSum = 0;
        int matchFinalScore = 0;
        int matchWin = 0;
        int matchLose = 0;
        int matchWinningRate = 0;

        SummonerDto summonerDto = riotService.getSummonerDtoWithRiotAPIBySummonerName(name);

        System.out.println("실행중");

        // TODO: SummonerInfoDto와 매핑 필요
        Optional<Summoner> summonerOptional = summonerJpaRepo.findById(summonerDto.getAccountId());
        if (summonerOptional.isPresent() && !reload) {
            Summoner summonerFromDB = summonerOptional.get();
            List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
            for (Match match : summonerFromDB.getMatches()) {

                summonerMatchDtoList.add(modelMapper.map(match,SummonerMatchDto.class));
                // match -> summonerMatchDtoList
//                summonerMatchDtoList.add(SummonerMatchDto.builder()
//                        .matchAssists(match.getMatchAssists())
//                        .matchChampion(match.getMatchChampion())
//                        .matchDealRank(match.getMatchDealRank())
//                        .matchDeaths(match.getMatchDeaths())
//                        .matchKdaScoreRank(match.getMatchKdaScoreRank())
//                        .matchKills(match.getMatchKills())
//                        .matchRank(match.getMatchRank())
//                        .matchTankRank(match.getMatchTankRank())
//                        .matchTowerDealRank(match.getMatchTowerDealRank())
//                        .matchWin(match.isMatchWin())
//                        .build()
//                );
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
                    SummonerMatchDto result = DeathnoteServiceHelper.getMatchScore(riotService.getMatchDtoWithRiotAPIByMatchId(gameId), encryptedAccountId);
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


    public TrollerRankerResponseDto getTrollerRankerListWithNum(int num) {
        Pageable trollerRankerPageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "trollerScore"));
        List<Summoner> summonerList = summonerJpaRepo.findTrollerRanker(num, trollerRankerPageable);
        List<TrollerRankerDto> trollerRankerDtoList = new ArrayList<>();
        for (Summoner summoner : summonerList) {
            trollerRankerDtoList.add(TrollerRankerDto
                    .builder()
                    .summonerIcon(summoner.getProfileIconId())
                    .trollerScore(summoner.getTrollerScore())
                    .summonerLevel(summoner.getSummonerLevel())
                    .summonerName(summoner.getSummonerName())
                    .SummonerRank(summoner.getSummonerRank())
                    .SummonerTier(summoner.getSummonerTier())
                    .build()
            );
        }
        return TrollerRankerResponseDto.builder().trollerRankerDtoList(trollerRankerDtoList).build();
    }


    static Pageable keywordPageable = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "updatedAt"));

    public SummonerKeywordResponseDto getSummonerNameWithKeyword(String keyword) {
        String formattedKeyword = NameFormatter.getFormattedSummonerName(keyword);
        if (formattedKeyword.equals("")) {
            return null;
        }
        List<Summoner> summonerList = summonerJpaRepo.search(formattedKeyword, keywordPageable);
        List<SummonerKeywordDto> summonerKeywordDtoList = new ArrayList<>();
        for (Summoner summoner : summonerList) {
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



}
