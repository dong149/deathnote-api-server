package com.rest.api.controller.deathnote;


import com.rest.api.dto.SummonerDto;
import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.response.rank.TrollerRankerResponseDto;
import com.rest.api.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.service.deathnote.DeathnoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/deathnote")
public class DeathnoteController {

    private final DeathnoteService deathnoteService;


    @GetMapping(value = "/summoner")
    public ResponseEntity<BaseResponseDto> getSummonerInfo(@RequestParam String name, boolean reload) {
        SummonerInfoDto summonerInfoDto = null;
        try {
            System.out.println(name);
            summonerInfoDto = deathnoteService.getSummonerInfoDtoWithSummonerName(name, reload);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

//    @ApiOperation(value = "소환자 정보", notes = "이름을 통해 소환사 정보를 return한다.")
//    @GetMapping(value = "/summoner")
//    public SummonerDto getSummoner(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {



    @ApiOperation(value = "실시간 트롤러 랭커 정보", notes= "실시간 트롤러 랭커 정보를 return한다.")
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


}
