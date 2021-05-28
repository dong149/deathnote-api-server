package com.rest.api.advice;


import com.rest.api.dto.response.ErrorResponseDto;
import com.rest.api.enumerator.ErrorType;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.rest.api.controller")
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler(SummonerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(SummonerNotFoundException e){
        log.error(e.getMessage(),e);
        return error(ErrorType.NOT_FOUND_ERROR,HttpStatus.NOT_FOUND);
    }
    private ResponseEntity<ErrorResponseDto> error(final ErrorType errorType, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new ErrorResponseDto(errorType.getErrorCode(), errorType.getErrorMessage()), httpStatus);
    }

    private ResponseEntity<ErrorResponseDto> error(final ErrorType errorType, final HttpStatus httpStatus, String customMessage) {
        return new ResponseEntity<>(new ErrorResponseDto(errorType.getErrorCode(), customMessage), httpStatus);
    }

    //TODO: Exception Handler 추가 설정해주기.
}


