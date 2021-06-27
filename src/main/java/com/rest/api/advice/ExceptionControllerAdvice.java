package com.rest.api.advice;


import com.rest.api.dto.response.ErrorResponseDto;
import com.rest.api.enumerator.ErrorType;
import com.rest.api.exception.riot.RiotAPIBadRequestException;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionControllerAdvice {
    /**
     * @param e : RiotAPIService 에서 발생하는 에러들
     * @return : ErrorResponseDto
     */
    @ExceptionHandler(SummonerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSummonerNotFoundException(SummonerNotFoundException e) {
        log.error(e.getMessage(), e);
        return error(ErrorType.NOT_FOUND_ERROR, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RiotAPIBadRequestException.class)
    private ResponseEntity<ErrorResponseDto> BadRequestException(RiotAPIBadRequestException e) {
        log.error(e.getMessage(), e);
        return error(ErrorType.BAD_REQUEST_ERROR, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDto> error(final ErrorType errorType, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new ErrorResponseDto(errorType.getErrorCode(), errorType.getErrorMessage(), new ArrayList<>()), httpStatus);
    }

    private ResponseEntity<ErrorResponseDto> error(final ErrorType errorType, final HttpStatus httpStatus, String customMessage) {
        return new ResponseEntity<>(new ErrorResponseDto(errorType.getErrorCode(), customMessage, new ArrayList<>()), httpStatus);
    }

    //TODO: Exception Handler 추가 설정해주기.
}


