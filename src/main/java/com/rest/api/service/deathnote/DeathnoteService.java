package com.rest.api.service.deathnote;

import com.rest.api.dto.*;
import com.rest.api.dto.response.rank.TrollerRankerResponseDto;
import com.rest.api.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.entity.summoner.Match;
import com.rest.api.entity.summoner.Summoner;
import com.rest.api.enumerator.QueueType;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import com.rest.api.repository.MatchJpaRepo;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.service.riot.RiotService;
import com.rest.api.util.NameFormatter;
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

        SummonerDto summonerDto = riotService.getSummonerDtoWithRiotAPIBySummonerName(name);
        Optional<Summoner> summonerOptional = summonerJpaRepo.findById(summonerDto.getAccountId());

        /*
        기존에 Summoner가 존재하고 reload가 필요없으면 기존 값을 return 해준다.
         */
        if (isSummonerExistInDBAndNotReload(summonerOptional, reload)) {
            return returnSummonerInfoNotReloaded(summonerOptional);
        }

        /*
        Reload가 필요하고, 기존의 Match가 존재하면 모두 삭제한다.
         */
        deleteAllMatchByAccountIdWhenReloadIsTrue(reload, summonerDto);

        MatchListDto matchListDto = riotService.getMatchListDtoWithRiotAPIByEncryptedAccountIdAndQueueAndSeason(summonerDto.getAccountId(), QueueType.SOLO_RANK_QUEUE, CURRENT_SEASON);
        LeagueEntryDto leagueEntryDto = riotService.getLeagueEntryDtoWithRiotAPIByEncryptedId(summonerDto.getId());
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        String encryptedAccountId = summonerDto.getAccountId();
        List<MatchReferenceDto> matchReferenceDtoList = matchListDto.getMatches();
        List<Long> gameIdList = getGameIdListLimitedByGameMaxSize(matchReferenceDtoList);
        final List<Future<SummonerMatchDto>> futures = new ArrayList<>();
        matchCnt = gameIdList.size();
        /*
        gameID에 해당하는 연산을 비동기적으로 처리한다.
         */
        for (Long gameId : gameIdList) {
            Callable<SummonerMatchDto> callable = () -> DeathnoteServiceHelper.getMatchScore(riotService.getMatchDtoWithRiotAPIByMatchId(gameId), encryptedAccountId);
            futures.add(executor.submit(callable));
        }

        try {

            for (Future<SummonerMatchDto> item : futures) {
                SummonerMatchDto summonerMatchDtoFuture = item.get();
                summonerMatchDtoList.add(summonerMatchDtoFuture);
                matches.add(summonerMatchDtoFuture.toEntity(summonerDto.getAccountId()));


                if (summonerMatchDtoFuture.isMatchWin()) {
                    matchScoreSum += summonerMatchDtoFuture.getMatchRank();
                } else {
                    matchScoreSum += summonerMatchDtoFuture.getMatchRank() - 1;
                }
            }
            printLogger("gameId 비동기 연산 완료");
        } catch (Exception e) {
            printLogger("gameId 비동기 연산 실패");
        }


        matchFinalScore = getMatchFinalScore(matchCnt, matchScoreSum);
        Summoner summoner;

        if (!reload) { // not reload 새롭게 생성
            summoner = getSummonerWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDtoAndMatches(matchFinalScore, summonerDto, leagueEntryDto, matches);
        } else {  // reload
            summoner = getSummonerById(summonerDto);
            summoner.reload(
                    summonerDto.getName(),
                    NameFormatter.getFormattedSummonerName(summonerDto.getName()),
                    leagueEntryDto.getRank(),
                    leagueEntryDto.getTier(),
                    matchFinalScore,
                    summonerDto.getProfileIconId(),
                    summonerDto.getSummonerLevel(),
                    leagueEntryDto.getWins() + leagueEntryDto.getLosses(),
                    leagueEntryDto.getWins(),
                    leagueEntryDto.getLosses(),
                    (int) ((1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins() + leagueEntryDto.getLosses()) * 100),
                    matches
            );

        }
        summonerJpaRepo.save(summoner);
        return getSummonerInfoDtoWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDto(matchFinalScore, summonerDto, leagueEntryDto, summonerMatchDtoList);


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
        return TrollerRankerResponseDto.builder().rankList(trollerRankerDtoList).build();
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


    /*
    private methods
     */

    private int getMatchFinalScore(int matchCnt, int matchScoreSum) {
        return (int) ((10 - ((1.0) * matchScoreSum / matchCnt)) * 10);
    }

    private List<Long> getGameIdListLimitedByGameMaxSize(List<MatchReferenceDto> matchReferenceDtoList) {
        List<Long> gameIdList = new ArrayList<>();

        // MatchId를 gameIdList에 저장합니다.
        for (MatchReferenceDto curMatchReferenceDto : matchReferenceDtoList) {
            gameIdList.add(curMatchReferenceDto.getGameId());
            if (gameIdList.size() >= GAME_MAX_SIZE) {
                break;
            }
        }
        return gameIdList;
    }

    private void deleteAllMatchByAccountIdWhenReloadIsTrue(boolean reload, SummonerDto summonerDto) {
        if (reload) {
            matchJpaRepo.deleteAllByMatchAccountId(summonerDto.getAccountId());
            printLogger("Match 기존 정보 삭제");
        }
    }

    private SummonerInfoDto returnSummonerInfoNotReloaded(Optional<Summoner> summonerOptional) {
        Summoner summonerFromDB = summonerOptional.get();
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        for (Match match : summonerFromDB.getMatches()) {
            summonerMatchDtoList.add(modelMapper.map(match, SummonerMatchDto.class));
        }

        printLogger("DB에서 불러오기 성공");
        return getSummonerInfoDtoWithSummonerFromDBAndSummonerMatchDtoList(summonerFromDB, summonerMatchDtoList);
    }



    private Summoner getSummonerById(SummonerDto summonerDto) {
        return summonerJpaRepo.findById(summonerDto.getAccountId()).orElseThrow(() -> {
            throw new SummonerNotFoundException("Summoner를 찾을 수 없습니다. Reload시 발생");
        });
    }


    private void printLogger(String msg) {
        logger.info(msg);
    }

    private boolean isSummonerExistInDBAndNotReload(Optional<Summoner> summonerOptional, boolean reload) {
        return summonerOptional.isPresent() && !reload;
    }


    private Summoner getSummonerWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDtoAndMatches(int matchFinalScore, SummonerDto summonerDto, LeagueEntryDto leagueEntryDto, List<Match> matches) {
        return Summoner.builder()
                .summonerName(summonerDto.getName())
                .summonerDecodedName(NameFormatter.getFormattedSummonerName(summonerDto.getName()))
                .summonerRank(leagueEntryDto.getRank())
                .summonerTier(leagueEntryDto.getTier())
                .trollerScore(matchFinalScore)
                .accountId(summonerDto.getAccountId())
                .summonerId(summonerDto.getId())
                .profileIconId(summonerDto.getProfileIconId())
                .summonerLevel(summonerDto.getSummonerLevel())
                .matchCount(leagueEntryDto.getWins() + leagueEntryDto.getLosses())
                .matchWin(leagueEntryDto.getWins())
                .matchLose(leagueEntryDto.getLosses())
                .matchWinningRate((int) ((1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins() + leagueEntryDto.getLosses()) * 100))
                .matches(matches)
                .build();
    }


    private SummonerInfoDto getSummonerInfoDtoWithSummonerFromDBAndSummonerMatchDtoList(Summoner summonerFromDB, List<SummonerMatchDto> summonerMatchDtoList) {
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
                .updatedAt(summonerFromDB.getUpdatedAt())
                .build();
    }


    private SummonerInfoDto getSummonerInfoDtoWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDto(int matchFinalScore, SummonerDto summonerDto, LeagueEntryDto leagueEntryDto, List<SummonerMatchDto> summonerMatchDtoList) {
        return SummonerInfoDto.builder()
                .trollerScore(matchFinalScore)
                .accountId(summonerDto.getAccountId())
                .summonerTier(leagueEntryDto.getTier())
                .summonerRank(leagueEntryDto.getRank())
                .summonerName(summonerDto.getName())
                .summonerMatch(summonerMatchDtoList)
                .summonerLevel(summonerDto.getSummonerLevel())
                .summonerIcon(summonerDto.getProfileIconId())
                .matchWinningRate((int) ((1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins() + leagueEntryDto.getLosses()) * 100))
                .matchLose(leagueEntryDto.getLosses())
                .matchWin(leagueEntryDto.getWins())
                .matchCount(leagueEntryDto.getWins() + leagueEntryDto.getLosses())
                .build();
    }


}
