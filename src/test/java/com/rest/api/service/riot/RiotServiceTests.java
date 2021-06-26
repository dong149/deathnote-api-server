package com.rest.api.service.riot;

import com.rest.api.dto.SummonerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.profiles.active:local")
public class RiotServiceTests {


    @Autowired
    private RiotService riotService;

    @Test
    @DisplayName("RiotAPI의 정상 작동을 확인합니다.")
    public void getRiotAPIWithSummonerName(){
        String name = "hide on bush";
        SummonerDto summonerDto = null;
        try{
            summonerDto = riotService.getSummonerDtoWithRiotAPIBySummonerName(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        assert summonerDto != null;
        assertEquals("Hide on bush",summonerDto.getName());
    }

}