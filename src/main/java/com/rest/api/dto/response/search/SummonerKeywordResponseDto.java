package com.rest.api.dto.response.search;

import com.rest.api.dto.SummonerKeywordDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Getter
@Setter
public class SummonerKeywordResponseDto {
    private List<SummonerKeywordDto> summonerKeywordDtoList;
}
