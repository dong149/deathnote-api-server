package com.rest.api.model.dto.response.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.api.model.entity.report.Report;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponseDto {

    private final Long reportId;
    private final boolean isReport;
    private final String summonerName;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReportResponseDto(Report report) {
        this(report.getReportId(), report.isReport(), report.getSummonerName(), report.getContent(),
             report.getCreatedAt(), report.getUpdatedAt());
    }

    public static ReportResponseDto of(Report report) {
        return ReportResponseDto.builder()
                                .reportId(report.getReportId())
                                .isReport(report.isReport())
                                .summonerName(report.getSummonerName())
                                .content(report.getContent())
                                .createdAt(report.getCreatedAt())
                                .updatedAt(report.getUpdatedAt())
                                .build();
    }

    public static List<ReportResponseDto> of(List<Report> reports) {
        return reports.stream()
                      .map(report -> ReportResponseDto.of(report))
                      .collect(Collectors.toList());
    }

    public ReportResponseDto(
        Long reportId, boolean isReport, String summonerName, String content, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.reportId = reportId;
        this.isReport = isReport;
        this.summonerName = summonerName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
