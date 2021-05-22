package com.rest.api.controller.report;


import com.rest.api.dto.report.ReportRequestDto;
import com.rest.api.entity.Report;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.repository.ReportJpaRepo;
import com.rest.api.service.response.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class ReportController {


    private final ResponseService responseService;

    @Autowired
    ReportJpaRepo reportJpaRepo;


    @ApiOperation(value = "리폿", notes = "소환사 이름을 통해 report 데이터를 받아온다.")
    @GetMapping(value = "/report")
    public ListResult<Report> getReportByName(@ApiParam(value = "소환사 이름", required = true) @RequestParam String summonerName) {
        // 띄어쓰기, 대문자 구분 없이 저장
        String name = summonerName.replaceAll(" ", "").toLowerCase();
        System.out.println("리폿 정보를 불러왔습니다.");
        return responseService.getListResult(reportJpaRepo.findAllBySummonerName(name));

    }


    @ApiOperation(value = "리폿", notes = "리폿/칭찬 내용을 작성합니다.")
    @PostMapping(value = "/report")
    public SingleResult<Report> postReport(@RequestBody ReportRequestDto reportRequestDto) {
        Report report = reportRequestDto.toEntity();
        reportJpaRepo.save(report);
        return responseService.getSingleResult(report);
    }


    @ApiOperation(value = "리폿", notes = "리폿/칭찬 내용을 작성합니다.")
    @DeleteMapping(value = "/report")
    public CommonResult deleteReport(@RequestParam long id) {
        reportJpaRepo.deleteById(id);
        return responseService.getSuccessResult();
    }


}
