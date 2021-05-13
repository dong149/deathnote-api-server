package com.rest.api.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatInfo {
    public int participantId;
    public String type;
    public long stat;

    public StatInfo(int participantId,long stat){
        this.participantId = participantId;
        this.stat = stat;
    }
}
