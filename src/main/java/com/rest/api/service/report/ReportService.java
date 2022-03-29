package com.rest.api.service.report;


import com.rest.api.model.dto.request.report.ReportRequestDto;
import com.rest.api.model.dto.request.report.ReportUpdateRequestDto;
import com.rest.api.model.dto.response.report.ReportResponseDto;
import com.rest.api.model.entity.report.Report;
import com.rest.api.exception.report.ReportNotFoundException;
import com.rest.api.repository.ReportJpaRepo;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportJpaRepo reportJpaRepo;

    public ReportResponseDto createReport(ReportRequestDto reportRequestDto) {
        Report report = Report.builder()
                              .reportAccountId(reportRequestDto.getAccountId())
                              .summonerName(getFormattedSummonerName(reportRequestDto.getSummonerName()))
                              .content(reportRequestDto.getContent())
                              .isReport(reportRequestDto.isReport())
                              .build();
        reportJpaRepo.save(report);
        return new ReportResponseDto(report);
    }

    public List<ReportResponseDto> getReportResponsesWithAccountId(String accountId) {

        List<Report> reports = ReportServiceHelper.findAllExistingByAccountId(reportJpaRepo, accountId);
        return ReportResponseDto.of(reports)
                                .stream()
                                .sorted(Comparator.comparing(ReportResponseDto::getCreatedAt))
                                .collect(Collectors.toList());
    }

    public boolean updateReportWithId(Long id, ReportUpdateRequestDto reportUpdateRequestDto) {
        Report report = reportJpaRepo.findById(id).orElseThrow(() -> {
            throw new ReportNotFoundException("Report update 에러");
        });
        report.update(reportUpdateRequestDto.getContent());
        reportJpaRepo.save(report);
        return true;
    }

    public boolean removeReportWithId(Long id) {
        if (!reportJpaRepo.existsById(id)) {
            throw new ReportNotFoundException(id);
        }
        reportJpaRepo.deleteById(id);
        return true;
    }

    // 이름을 공통된 format으로 활용하기 위함입니다.
    public static String getFormattedSummonerName(String summonerName) {
        return summonerName.replaceAll(" ", "").toLowerCase();
    }
}

