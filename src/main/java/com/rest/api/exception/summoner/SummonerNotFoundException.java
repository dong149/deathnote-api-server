package com.rest.api.exception.summoner;

public class SummonerNotFoundException extends RuntimeException {

    public SummonerNotFoundException() {
    }

    public SummonerNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SummonerNotFoundException(String msg) {
        super(msg);
    }
}
