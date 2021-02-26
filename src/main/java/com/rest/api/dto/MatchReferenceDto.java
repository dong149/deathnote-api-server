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
public class MatchReferenceDto {
    private long gameId;
    private String role;
    private int season;
    private String platformId;
    private int champion;
    private int queue;
    private String lane;
    private long timestamp;
}