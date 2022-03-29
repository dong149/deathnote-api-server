package com.rest.api.advice;


import com.rest.api.model.dto.response.ErrorResponseDto;
import com.rest.api.enumerator.ErrorType;
import com.rest.api.exception.note.NoteNotFoundException;
import com.rest.api.exception.report.ReportNotFoundException;
import com.rest.api.exception.riot.RiotAPIBadRequestException;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(SummonerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSummonerNotFoundException(
        SummonerNotFoundException e) {

        log.error(e.getMessage(), e);
        return error(ErrorType.SUMMONER_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleReportNotFoundException(
        ReportNotFoundException e) {

        log.error(e.getMessage(), e);
        return error(ErrorType.REPORT_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoteNotFoundException(NoteNotFoundException e) {
        log.error(e.getMessage(), e);
        return error(ErrorType.NOTE_NOT_FOUND_ERROR, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RiotAPIBadRequestException.class)
    private ResponseEntity<ErrorResponseDto> BadRequestException(RiotAPIBadRequestException e) {
        log.error(e.getMessage(), e);
        return error(ErrorType.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDto> error(
        final ErrorType errorType,
        final HttpStatus httpStatus) {

        return new ResponseEntity<>(
            new ErrorResponseDto(
                errorType.getErrorCode(),
                errorType.getErrorMessage(),
                new ArrayList<>()), httpStatus);
    }

    private ResponseEntity<ErrorResponseDto> error(
        final ErrorType errorType,
        final HttpStatus httpStatus,
        String customMessage) {

        return new ResponseEntity<>(
            new ErrorResponseDto(
                errorType.getErrorCode(),
                customMessage,
                new ArrayList<>()), httpStatus);
    }
}


