package com.rest.api.controller.report;


import com.rest.api.dto.request.report.ReportRequestDto;
import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.response.ErrorResponseDto;
import com.rest.api.dto.response.report.ReportListResponseDto;
import com.rest.api.dto.response.report.ReportResponseDto;
import com.rest.api.service.report.ReportService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {


    private final ReportService reportService;

    @ApiOperation(value = "report", notes = "report 생성", response = ReportResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "report 생성 성공"),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @PostMapping
    public ResponseEntity<BaseResponseDto> createReport(@RequestBody ReportRequestDto reportRequestDto) {
        ReportResponseDto reportResponseDto = reportService.createReport(reportRequestDto);
        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.CREATED.value(), "데이터 생성 성공", reportResponseDto.getReportId()), HttpStatus.CREATED);
    }


    @ApiOperation(value = "report", notes = "report 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "report 조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 report 접근"),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @GetMapping
    public ResponseEntity<ReportListResponseDto> getReportByName(@ApiParam(value = "소환사 이름", required = true) @RequestParam String name) {
        List<ReportResponseDto> reportResponseDtos = reportService.getReportResponsesWithSummonerName(name);
        return new ResponseEntity<>(new ReportListResponseDto(HttpStatus.OK.value(), "데이터 조회 성공", reportResponseDtos), HttpStatus.OK);
    }

    // TODO : 나머지 UPDATE, DELETE 등 구현하기

    @ApiOperation(value = "report", notes = "report 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "report 수정 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 report 접근"),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @PutMapping
    public ResponseEntity<ReportListResponseDto> updateReportByReportId(@ApiParam(value = "소환사 이름", required = true) @RequestParam String reportId) {
        List<ReportResponseDto> reportResponseDtos = null;
        // TODO: Report update by Id. (check reachable report contents)

        return new ResponseEntity<>(new ReportListResponseDto(HttpStatus.OK.value(), "데이터 수정 성공", reportResponseDtos), HttpStatus.OK);
    }
//
//
//    @ApiOperation(value = "리폿", notes = "리폿/칭찬 내용을 작성합니다.")
//    @DeleteMapping(value = "/report")
//    public CommonResult deleteReport(@RequestParam long id) {
//        reportJpaRepo.deleteById(id);
//        return responseService.getSuccessResult();
//    }


}
