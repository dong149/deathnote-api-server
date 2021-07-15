package com.rest.api.enumerator;


import lombok.Getter;

@Getter
public enum ErrorType {

    UNEXPECTED_ERROR(-1, "예상하지 못한 에러입니다"),
    REST_ERROR(1, "통신에 문제가 발생했습니다"),
    NOT_FOUND_ERROR(404, "찾을 수 없는 리소스입니다"),
    BAD_REQUEST_ERROR(400, "필수 값이 없거나, 잘못된 값이 입력되없습니다"),
    ;

    private final int errorCode;
    private final String errorMessage;

    ErrorType(int responseCode,String errorMessage){
        this.errorCode = responseCode;
        this.errorMessage = errorMessage;
    }




}
