package com.rest.api.dto.response.report;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReportListResponseDto {
    private int status;
    private int reportCount;
    private int noReportCount;
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object object;


    public ReportListResponseDto(int status,String message, List<ReportResponseDto> reportResponseDtos ){
        int reportCount = 0;
        int noReportCount = 0;

        for (ReportResponseDto cur : reportResponseDtos) {
            if (cur.isReport())
                reportCount++;
            else
                noReportCount++;
        }
        this.status = status;
        this.message = message;
        this.reportCount = reportCount;
        this.noReportCount = noReportCount;
        this.object = reportResponseDtos;
    }
}

