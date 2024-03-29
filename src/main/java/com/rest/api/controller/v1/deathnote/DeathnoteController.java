package com.rest.api.controller.v1.deathnote;

import com.rest.api.batch.DeathnoteBatch;
import com.rest.api.model.dto.response.BaseResponseDto;
import com.rest.api.model.dto.response.rank.TrollerRankerResponseDto;
import com.rest.api.model.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.model.dto.result.SummonerInfoDto;
import com.rest.api.service.v1.deathnote.DeathnoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/deathnote")
public class DeathnoteController {

    private final DeathnoteService deathnoteService;
    private final DeathnoteBatch deathnoteBatch;

    @ApiOperation(value = "summonerInfo", notes = "summonerInfo 조회")
    @GetMapping(value = "/summoner")
    public ResponseEntity<BaseResponseDto> getSummonerInfo(
        @ApiParam(value = "소환사 이름", required = true) @RequestParam String name,
        @ApiParam(value = "갱신 여부", required = true, defaultValue = "true") @RequestParam boolean reload) {

        SummonerInfoDto summonerInfoDto = deathnoteService.getSummonerInfoDtoWithSummonerName(name, reload);

        return new ResponseEntity<>(
            new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", summonerInfoDto),
            HttpStatus.OK);
    }

    @GetMapping(value = "/summoner/keyword")
    public ResponseEntity<BaseResponseDto> getSummonerNameWithKeyword(@RequestParam String keyword) {
        SummonerKeywordResponseDto summonerKeywordResponseDto = null;
        try {
            summonerKeywordResponseDto = deathnoteService.getSummonerNameWithKeyword(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
            new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", summonerKeywordResponseDto),
            HttpStatus.OK);
    }

    @ApiOperation(value = "실시간 트롤러 랭커 정보", notes = "실시간 트롤러 랭커 정보를 return한다.")
    @GetMapping(value = "/troller/rank")
    public ResponseEntity<BaseResponseDto> getTrollerRank(
        @ApiParam(value = "몇 명", required = true) @RequestParam int num) {
        TrollerRankerResponseDto trollerRankerResponseDto = null;
        try {
            trollerRankerResponseDto = deathnoteService.getTrollerRankerListWithNum(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
            new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", trollerRankerResponseDto),
            HttpStatus.OK);
    }

    @ApiOperation(value = "key", notes = "매치 정보 배치 작업")
    @GetMapping(value = "/match/batch")
    public String doMatchBatch(
        @ApiParam(value = "관리자 키", required = true) @RequestParam String key) {
        try {
            deathnoteBatch.doMatchUpdateBatch();
            return "완료 되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "배치 작업 중 오류가 발생했습니다.";
        }
    }
}
