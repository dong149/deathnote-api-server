package com.rest.api.service.v1.report;

import com.rest.api.model.entity.report.Report;
import com.rest.api.repository.ReportJpaRepo;
import java.util.List;

public class ReportServiceHelper {

    public static List<Report> findAllExistingBySummonerName(
        ReportJpaRepo reportJpaRepo, String summonerName) {
        return reportJpaRepo.findAllBySummonerName(summonerName);
    }

    public static List<Report> findAllExistingByAccountId(ReportJpaRepo reportJpaRepo, String accountId) {
        return reportJpaRepo.findAllByReportAccountId(accountId);
    }
}
