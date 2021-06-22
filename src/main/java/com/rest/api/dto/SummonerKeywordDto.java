package com.rest.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SummonerKeywordDto {
    private String summonerName;
    private Long summonerLevel;
    private int summonerIcon;
    private String SummonerTier;
    private String SummonerRank;
}