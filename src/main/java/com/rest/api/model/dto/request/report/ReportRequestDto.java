package com.rest.api.model.dto.request.report;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
