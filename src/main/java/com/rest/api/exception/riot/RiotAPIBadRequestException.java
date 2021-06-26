package com.rest.api.exception.riot;

public class RiotAPIBadRequestException extends RuntimeException {

    public RiotAPIBadRequestException(){}

    public RiotAPIBadRequestException(String msg,Throwable cause){super(msg,cause);}
    public RiotAPIBadRequestException(String msg){super(msg);}
}
