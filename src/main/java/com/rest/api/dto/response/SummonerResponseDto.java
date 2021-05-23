package com.rest.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.api.entity.Summoner;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummonerResponseDto {
    /**
     * Summoner 한 명당 정보 Response
     **/
    private Long id;

    private String name;

    // Encrypted account ID
    private String accountId;

    // Encrypted summoner ID
    private String summonerId;

    private int profileIconId;

    private Long summonerLevel;

    private LocalDateTime createdAt;

    public SummonerResponseDto(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
    public static SummonerResponseDto of(Summoner summoner){
        return new SummonerResponseDto(summoner.getName(),summoner.getCreatedAt());
    }



}
