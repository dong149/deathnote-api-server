package com.rest.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TrollerRankerDto {

    private String summonerName;
    private int trollerScore;
    private Long summonerLevel;
    private int summonerIcon;
    private String SummonerTier;
    private String SummonerRank;
}
