package com.rest.api.service.report;

import com.rest.api.entity.report.Report;
import com.rest.api.repository.ReportJpaRepo;

import java.util.List;

public class ReportServiceHelper {

    public static List<Report> findAllExistingBySummonerName(ReportJpaRepo reportJpaRepo,String summonerName){
        return reportJpaRepo.findAllBySummonerName(summonerName);
    }







}
