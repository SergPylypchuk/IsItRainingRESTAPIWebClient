package com.airtone.IsItRainingRestApiWebClient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class PostRequest {
    @Value("${measurementSendingUrl}")
    private String measurementSendingUrl;
    @Value("${sensorSendingUrl}")
    private String sensorSendingUrl;

    RestTemplate restTemplate = new RestTemplate();
    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, List<Map<String, String>>> dataOutsideMap = new HashMap<>();

    public PostRequest() {
    }

    public void measurementSending() {
        sending("measurement", measurementSendingUrl);
    }

    public void sensorSending() {
        sending("sensor", sensorSendingUrl);
    }

    public void sending(String dataType, String URL) {
        dataOutsideMap.put(dataType, dataList);
        HttpEntity<Map<String, List<Map<String, String>>>> request = new HttpEntity<>(dataOutsideMap);
        restTemplate.postForObject(URL, request, String.class);

        dataList.clear();
        dataOutsideMap.clear();
    }
}
