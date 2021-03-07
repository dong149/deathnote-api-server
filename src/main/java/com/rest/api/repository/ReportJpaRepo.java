package com.rest.api.repository;

import com.rest.api.entity.Report;
import com.rest.api.model.response.ListResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJpaRepo extends JpaRepository <Report, Long> {
    List<Report> findAllBySummonerName(String summonerName);

}
