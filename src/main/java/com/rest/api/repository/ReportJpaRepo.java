package com.rest.api.repository;

import com.rest.api.entity.report.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepo extends JpaRepository<Report, Long> {

    List<Report> findAllBySummonerName(String summonerName);

    List<Report> findAllByReportAccountId(String accountId);
}
