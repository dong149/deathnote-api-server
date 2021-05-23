package com.rest.api.service.riot;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RequiredArgsConstructor
@Service
public class RiotService {


    private final RestTemplate restTemplate;
//
//    @Value("${riot.API_KEY}")
//    private String API_KEY;



}
