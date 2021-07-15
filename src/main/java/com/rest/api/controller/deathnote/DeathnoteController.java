package com.rest.api.controller.deathnote;


import com.rest.api.dto.SummonerDto;
import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.response.ErrorResponseDto;
import com.rest.api.dto.response.rank.TrollerRankerResponseDto;
import com.rest.api.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.service.deathnote.DeathnoteService;
import com.rest.api.service.deathnote.batch.DeathnoteBatch;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/deathnote")
public class DeathnoteController {

    private final DeathnoteService deathnoteService;
    private final DeathnoteBatch deathnoteBatch;

    @Value("${deathnote.key}")
    private String ADMIN_KEY;

    @ApiOperation(value = "summonerInfo", notes = "summonerInfo 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "존재하지 않는 summoner 접근", response = ErrorResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @GetMapping(value = "/summoner")
    public ResponseEntity<BaseResponseDto> getSummonerInfo(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name, @ApiParam(value = "갱신 여부", required = true) @RequestParam boolean reload){
        SummonerInfoDto summonerInfoDto = deathnoteService.getSummonerInfoDtoWithSummonerName(name, reload);

        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", summonerInfoDto), HttpStatus.OK);
    }


    @GetMapping(value = "/summoner/keyword")
    public ResponseEntity<BaseResponseDto> getSummonerNameWithKeyword(@RequestParam String keyword) {
        SummonerKeywordResponseDto summonerKeywordResponseDto = null;
        try {
            summonerKeywordResponseDto = deathnoteService.getSummonerNameWithKeyword(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", summonerKeywordResponseDto), HttpStatus.OK);
    }


    @ApiOperation(value = "실시간 트롤러 랭커 정보", notes = "실시간 트롤러 랭커 정보를 return한다.")
    @GetMapping(value = "/troller/rank")
    public ResponseEntity<BaseResponseDto> getTrollerRank(@ApiParam(value = "몇 명", required = true) @RequestParam int num) {
        TrollerRankerResponseDto trollerRankerResponseDto = null;
        try {
            trollerRankerResponseDto = deathnoteService.getTrollerRankerListWithNum(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", trollerRankerResponseDto), HttpStatus.OK);
    }





    @ApiOperation(value = "key", notes = "매치 정보 배치 작업")
    @GetMapping(value = "/match/batch")
    public String doMatchBatch(@ApiParam(value = "관리자 키", required = true) @RequestParam String key) {

        if(!ADMIN_KEY.equals(key)){
            return "키가 잘못되었습니다.";
        }
        try {
            deathnoteBatch.doMatchUpdateBatch();
            return "완료 되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "배치 작업 중 오류가 발생했습니다.";
        }
    }



}
