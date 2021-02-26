package com.rest.api.config;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;


/**
 * 다른 서버에 Rest요청을 할 때 필요한 설정을 해주는 클래스
 */
@Configuration
public class HttpConnectionConfig {

    @Value("${restTemplate.factory.readTimeout}")
    private int READ_TIMEOUT;

    @Value("${restTemplate.factory.connectTimeout}")
    private int CONNECT_TIMEOUT;

    @Value("${restTemplate.httpClient.maxConnTotal}")
    private int MAX_CONN_TOTAL;

    @Value("${restTemplate.httpClient.maxConnPerRoute}")
    private int MAX_CONN_PER_ROUTE;


    @Bean
    public RestTemplate restTemplate() {
        HttpHost proxy = new HttpHost("donghoon.tk", 80, "http");
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(MAX_CONN_TOTAL)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setProxy(proxy)
                .build();
        factory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}
