package com.rest.api.controller.v1.report;


import com.rest.api.model.dto.request.report.ReportRequestDto;
import com.rest.api.model.dto.request.report.ReportUpdateRequestDto;
import com.rest.api.model.dto.response.BaseResponseDto;
import com.rest.api.model.dto.response.ErrorResponseDto;
import com.rest.api.model.dto.response.report.ReportListResponseDto;
import com.rest.api.service.v1.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @ApiOperation(value = "report", notes = "report 생성")
    @ApiResponses({
        @ApiResponse(code = 200, message = "report 생성 성공"),
        @ApiResponse(code = 500, message = "서버 에러"),
    })
    @PostMapping
    public ResponseEntity<BaseResponseDto> createReport(@RequestBody ReportRequestDto reportRequestDto) {

        return new ResponseEntity<>(
            new BaseResponseDto(
                HttpStatus.CREATED.value(),
                "데이터 생성 성공",
                reportService.createReport(reportRequestDto)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "report", notes = "report 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "report 조회 성공"),
        @ApiResponse(code = 404, message = "존재하지 않는 report 접근"),
        @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @GetMapping
    public ResponseEntity<ReportListResponseDto> getReportByAccountId(
        @ApiParam(value = "소환사 ID", required = true) @RequestParam String accountId) {

        return new ResponseEntity<>(
            new ReportListResponseDto(
                HttpStatus.OK.value(),
                "데이터 조회 성공",
                reportService.getReportResponsesWithAccountId(accountId)), HttpStatus.OK);
    }

    @ApiOperation(value = "report", notes = "report 수정")
    @ApiResponses({
        @ApiResponse(code = 200, message = "report 수정 성공"),
        @ApiResponse(code = 404, message = "존재하지 않는 report 접근"),
        @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponseDto> updateReportByReportId(
        @ApiParam(value = "reportId", required = true) @PathVariable Long id,
        @RequestBody ReportUpdateRequestDto reportUpdateRequestDto) {

        return new ResponseEntity<>(
            new BaseResponseDto(
                HttpStatus.OK.value(),
                "데이터 수정 성공",
                reportService.updateReportWithId(id, reportUpdateRequestDto)), HttpStatus.OK);
    }

    // TODO : 삭제 방식을 그냥 바로 삭제하는 것 -> deletedAt Column 을 추가해주는 걸로 변경
    @ApiOperation(value = "report", notes = "report 삭제")
    @ApiResponses({
        @ApiResponse(code = 200, message = "report 삭제 성공"),
        @ApiResponse(code = 404, message = "존재하지 않는 report 접근"),
        @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponseDto.class),
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseDto> deleteReportByReportId(
        @ApiParam(value = "reportId", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(
            new BaseResponseDto(
                HttpStatus.OK.value(),
                "데이터 삭제 성공",
                reportService.removeReportWithId(id)), HttpStatus.OK);
    }
}