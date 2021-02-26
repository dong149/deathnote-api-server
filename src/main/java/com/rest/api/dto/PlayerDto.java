package com.rest.api.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto {
    private int profileIcon;
    private String accountId;
    private String matchHistoryUri;
    private String currentAccountId;
    private String currentPlatformId;
    private String summonerName;
    private String summonerId;
    private String platformId;

}
