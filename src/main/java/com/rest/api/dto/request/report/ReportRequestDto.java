package com.rest.api.dto.request.report;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rest.api.entity.report.Report;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    private boolean isReport;

    @NotBlank
    private String accountId;

    @NotBlank
    @Size(max = 50)
    private String summonerName;

    @NotBlank
    @Size(max = 200)
    private String content;

}
