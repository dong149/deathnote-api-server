package com.rest.api.controller.deathnote;


import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.response.search.SummonerKeywordResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.service.deathnote.DeathnoteService;
import io.swagger.annotations.Api;
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

}
