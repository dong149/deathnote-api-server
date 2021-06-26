package com.rest.api.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class NameFormatter {
    // 이름을 공통된 format으로 활용하기 위함입니다.
    public static String getFormattedSummonerName(String summonerName) {
        System.out.println(URLDecoder.decode(summonerName, StandardCharsets.UTF_8).replaceAll("\\s", "").toLowerCase());
        return URLDecoder.decode(summonerName, StandardCharsets.UTF_8).replaceAll("\\s", "").toLowerCase();

    }
}
