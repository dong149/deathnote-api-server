package com.rest.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.api.model.entity.summoner.Summoner;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Summoner 한 명당 정보 Response
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummonerResponseDto {

    private Long id;
    private String name;
    /**
     * Encrypted account ID
     */
    private String accountId;
    /**
     * Encrypted summoner ID
     */
    private String summonerId;
    private int profileIconId;
    private Long summonerLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SummonerResponseDto(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public static SummonerResponseDto of(Summoner summoner) {
        return new SummonerResponseDto(summoner.getSummonerName(), summoner.getUpdatedAt());
    }
}
