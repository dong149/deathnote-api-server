package com.rest.api.controller.deathnote;


import com.rest.api.controller.riot.RiotAPIController;
import com.rest.api.dto.*;
import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.service.deathnote.DeathnoteService;
import com.rest.api.service.summonerInfo.SummonerInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/deathnote")
public class DeathnoteController {

    private final DeathnoteService deathnoteService;


    @GetMapping(value = "/summoner")
    public ResponseEntity<BaseResponseDto> getSummonerInfo(@RequestParam String name,boolean reload) {
        SummonerInfoDto summonerInfoDto = null;
        try{
            System.out.println(name);
            summonerInfoDto = deathnoteService.getSummonerInfoDtoWithSummonerName(name,reload);
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", summonerInfoDto), HttpStatus.OK);
    }

}
