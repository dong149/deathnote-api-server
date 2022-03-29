package com.rest.api.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class NameFormatUtils {

    public static String getFormattedSummonerName(String summonerName) {
        return URLDecoder.decode(summonerName, StandardCharsets.UTF_8).replaceAll("\\s", "").toLowerCase();
    }
}
