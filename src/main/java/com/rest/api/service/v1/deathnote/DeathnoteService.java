package com.rest.api.service.v1.deathnote;

import com.rest.api.adapter.riot.RiotApiAdapter;
import com.rest.api.enumerator.QueueType;
import com.rest.api.model.dto.LeagueEntryDto;
import com.rest.api.model.dto.SummonerDto;
import com.rest.api.model.dto.SummonerKeywordDto;
import com.rest.api.model.dto.TrollerRankerDto;
import com.rest.api.model.dto.response.rank.TrollerRankerResponseDto;
import com.rest.api.model.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.model.dto.result.SummonerInfoDto;
import com.rest.api.model.dto.result.SummonerMatchDto;
import com.rest.api.model.entity.summoner.Match;
import com.rest.api.model.entity.summoner.Summoner;
import com.rest.api.repository.MatchJpaRepo;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.util.NameFormatUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeathnoteService {

    private final RiotApiAdapter riotApiAdapter;
    private final SummonerJpaRepo summonerJpaRepo;
    private final MatchJpaRepo matchJpaRepo;
    private final ModelMapper modelMapper;
    private final ExecutorService executor;

    @Value("${lol.current.season}")
    private int CURRENT_SEASON;

    @Value("${lol.game.size}")
    private int GAME_MAX_SIZE;


    public SummonerInfoDto getSummonerInfoDtoWithSummonerName(String name, boolean reload) {

        int matchCnt;
        int matchFinalScore;
        int matchScoreSum = 0;

        SummonerDto summonerDto = riotApiAdapter.getSummonerDtoWithRiotAPIBySummonerName(name);
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

        List<String> matchIds = riotApiAdapter.getMatchListDto(
            summonerDto.getPuuid(),
            QueueType.SOLO_RANK_QUEUE,
            CURRENT_SEASON);

        LeagueEntryDto leagueEntryDto = riotApiAdapter.getLeagueEntryDtoWithRiotAPIByEncryptedId(
            summonerDto.getId());
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        List<Match> matches = new ArrayList<>();
        String encryptedAccountId = summonerDto.getAccountId();

        final List<Future<SummonerMatchDto>> futures = new ArrayList<>();
        matchCnt = matchIds.size();
        /*
        gameID에 해당하는 연산을 비동기적으로 처리한다.
         */
        for (String matchId : matchIds) {
            Callable<SummonerMatchDto> callable = () -> DeathnoteServiceHelper.getMatchScore(
                riotApiAdapter.getMatchDtoWithRiotAPIByMatchId(matchId), encryptedAccountId);
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
        } catch (Exception e) {
            log.error("gameId async fail");
        }

        matchFinalScore = getMatchFinalScore(matchCnt, matchScoreSum);
        Summoner summoner = getSummonerById(summonerDto);

        if (!reload || summoner == null) { // not reload 새롭게 생성
            summoner = getSummonerWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDtoAndMatches(
                matchFinalScore, summonerDto, leagueEntryDto, matches);
        } else {  // reload
            summoner.reload(
                summonerDto.getName(),
                NameFormatUtils.getFormattedSummonerName(summonerDto.getName()),
                leagueEntryDto.getRank(),
                leagueEntryDto.getTier(),
                matchFinalScore,
                summonerDto.getProfileIconId(),
                summonerDto.getSummonerLevel(),
                leagueEntryDto.getWins() + leagueEntryDto.getLosses(),
                leagueEntryDto.getWins(),
                leagueEntryDto.getLosses(),
                (int) (
                    (1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins() + leagueEntryDto.getLosses())
                        * 100),
                matches
                           );

        }
        summonerJpaRepo.save(summoner);
        return getSummonerInfoDtoWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDto(
            matchFinalScore, summonerDto, leagueEntryDto, summonerMatchDtoList);


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
        String formattedKeyword = NameFormatUtils.getFormattedSummonerName(keyword);
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

    private List<String> getGameIdListLimitedByGameMaxSize(List<String> matchIds) {
        return matchIds;
    }

    private void deleteAllMatchByAccountIdWhenReloadIsTrue(boolean reload, SummonerDto summonerDto) {
        if (reload) {
            matchJpaRepo.deleteAllByMatchAccountId(summonerDto.getAccountId());
        }
    }

    private SummonerInfoDto returnSummonerInfoNotReloaded(Optional<Summoner> summonerOptional) {
        Summoner summonerFromDB = summonerOptional.get();
        List<SummonerMatchDto> summonerMatchDtoList = new ArrayList<>();
        for (Match match : summonerFromDB.getMatches()) {
            summonerMatchDtoList.add(modelMapper.map(match, SummonerMatchDto.class));
        }
        return getSummonerInfoDtoWithSummonerFromDBAndSummonerMatchDtoList(
            summonerFromDB, summonerMatchDtoList);
    }


    private Summoner getSummonerById(SummonerDto summonerDto) {
        return summonerJpaRepo.findById(summonerDto.getAccountId()).orElse(null);
    }

    private boolean isSummonerExistInDBAndNotReload(Optional<Summoner> summonerOptional, boolean reload) {
        return summonerOptional.isPresent() && !reload;
    }


    private Summoner getSummonerWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDtoAndMatches(
        int matchFinalScore, SummonerDto summonerDto, LeagueEntryDto leagueEntryDto, List<Match> matches) {
        return Summoner.builder()
                       .summonerName(summonerDto.getName())
                       .summonerDecodedName(NameFormatUtils.getFormattedSummonerName(summonerDto.getName()))
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
                       .matchWinningRate((int) ((1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins()
                           + leagueEntryDto.getLosses())
                           * 100))
                       .matches(matches)
                       .build();
    }


    private SummonerInfoDto getSummonerInfoDtoWithSummonerFromDBAndSummonerMatchDtoList(
        Summoner summonerFromDB, List<SummonerMatchDto> summonerMatchDtoList) {
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


    private SummonerInfoDto getSummonerInfoDtoWithMatchFinalScoreAndSummonerDtoAndLeagueEntryDto(
        int matchFinalScore, SummonerDto summonerDto, LeagueEntryDto leagueEntryDto,
        List<SummonerMatchDto> summonerMatchDtoList) {
        return SummonerInfoDto.builder()
                              .trollerScore(matchFinalScore)
                              .accountId(summonerDto.getAccountId())
                              .summonerTier(leagueEntryDto.getTier())
                              .summonerRank(leagueEntryDto.getRank())
                              .summonerName(summonerDto.getName())
                              .summonerMatch(summonerMatchDtoList)
                              .summonerLevel(summonerDto.getSummonerLevel())
                              .summonerIcon(summonerDto.getProfileIconId())
                              .matchWinningRate((int) (
                                  (1.0) * leagueEntryDto.getWins() / (leagueEntryDto.getWins()
                                      + leagueEntryDto.getLosses())
                                      * 100))
                              .matchLose(leagueEntryDto.getLosses())
                              .matchWin(leagueEntryDto.getWins())
                              .matchCount(leagueEntryDto.getWins() + leagueEntryDto.getLosses())
                              .build();
    }
}

