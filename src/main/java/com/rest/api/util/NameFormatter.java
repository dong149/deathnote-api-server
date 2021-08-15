package com.rest.api.util;

import com.rest.api.service.deathnote.DeathnoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class NameFormatter {
    private static Logger logger = LoggerFactory.getLogger(NameFormatter.class);
    // 이름을 공통된 format으로 활용하기 위함입니다.

    public static String getFormattedSummonerName(String summonerName) {
        logger.info(URLDecoder.decode(summonerName, StandardCharsets.UTF_8).replaceAll("\\s", "").toLowerCase());
        return URLDecoder.decode(summonerName, StandardCharsets.UTF_8).replaceAll("\\s", "").toLowerCase();
    }
}
