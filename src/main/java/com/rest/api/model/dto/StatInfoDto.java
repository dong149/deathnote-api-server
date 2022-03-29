package com.rest.api.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatInfoDto {

    public int participantId;
    public String type;
    public long stat;

    public StatInfoDto(int participantId, long stat) {
        this.participantId = participantId;
        this.stat = stat;
    }
}
