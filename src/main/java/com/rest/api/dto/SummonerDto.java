package com.rest.api.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerDto {
    /**
     * Summoner : 소환사 정보
     **/
    // Encrypted account ID
    private String accountId;

    // ID of the summoner icon associated with the summoner.
    private int profileIconId;

    // Date summoner was last modified specified as epoch milliseconds.
    // The following events will update this timestamp:
    // profile icon change, playing the tutorial or advanced tutorial, finishing a game, summoner name change
    private long revisionDate;

    // Summoner name
    private String name;

    // Encrypted summoner ID
    private String id;

    // Encrypted PUUID
    private String puuid;

    // Summoner level associated with the summoner.
//    @JsonProperty(required = true)
    private long summonerLevel;
}