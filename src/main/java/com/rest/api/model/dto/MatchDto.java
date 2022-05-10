package com.rest.api.model.dto;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MatchDto {

    // new
    private MetadataDto metadata;
    private InfoDto info;

    // legacy
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
