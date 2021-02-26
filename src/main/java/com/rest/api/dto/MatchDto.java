package com.rest.api.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDto {
    private long gameId;
    private List<ParticipantIdentityDto> participantIdentities;
    private int queueId;
    private String gameType;
    private String gameDuration;
    private List<TeamStatDto> teams;
    private String platformId;
    private long gameCreation;
    private int seasonId;
    private int mapId;
    private String gameMode;
    private List<ParticipantDto> participants;
}
