package com.rest.api.controller.deathnote;


import com.rest.api.controller.riot.RiotAPIController;
import com.rest.api.dto.*;
import com.rest.api.dto.result.SummonerInfoDto;
import com.rest.api.dto.result.SummonerMatchDto;
import com.rest.api.service.deathnote.DeathnoteService;
import com.rest.api.service.summonerInfo.SummonerInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(tags = {"2. Deathnote"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1")
public class DeathnoteController {

    private final DeathnoteService deathnoteService;

    @GetMapping(value = "/deathnote")
    public SummonerInfoDto getSummonerInfo(@RequestParam String name) {
        SummonerInfoDto summonerInfoDto = null;
        try{
            System.out.println(name);
            summonerInfoDto = deathnoteService.getSummonerInfoDtoWithSummonerName(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        return summonerInfoDto;
    }


}
