package com.rest.api.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MetadataDto {

    private String dataVersion;
    private String matchId;
}
