package com.rest.api.service.report;


import com.rest.api.dto.request.report.ReportRequestDto;
import com.rest.api.dto.response.report.ReportResponseDto;
import com.rest.api.entity.report.Report;
import com.rest.api.repository.ReportJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportJpaRepo reportJpaRepo;

    public ReportResponseDto createReport(ReportRequestDto reportRequestDto){
        Report report = Report.builder()
                .summonerName(reportRequestDto.getSummonerName())
                .content(reportRequestDto.getContent())
                .isReport(reportRequestDto.isReport())
                .build();
        reportJpaRepo.save(report);
        return new ReportResponseDto(report);
    }


    public List<ReportResponseDto> getReportResponsesWithSummonerName(String summonerName){

        List<Report> reports = ReportServiceHelper.findAllExistingBySummonerName(reportJpaRepo,getFormattedSummonerName(summonerName));
        return ReportResponseDto.of(reports)
                .stream()
                .sorted(Comparator.comparing(ReportResponseDto::getCreatedAt))
                .collect(Collectors.toList());
    }



    // 이름을 공통된 format으로 활용하기 위함입니다.
    public static String getFormattedSummonerName(String summonerName){
        return summonerName.replaceAll(" ", "").toLowerCase();
    }
}
