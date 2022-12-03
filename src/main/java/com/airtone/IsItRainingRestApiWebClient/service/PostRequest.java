package com.airtone.IsItRainingRestApiWebClient.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class PostRequest {
    RestTemplate restTemplate = new RestTemplate();
    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, List<Map<String, String>>> dataOutsideMap = new HashMap<>();

    public PostRequest() {
    }

    public void measurementSending() {
        String URL = "http://localhost:8080/measurements/add";
        sending("measurement", URL);
    }

    public void sensorSending() {
        String URL = "http://localhost:8080/sensors/registrationBatch";
        sending("sensor", URL);
    }

    public boolean sensorCheck(String uncheckedSensor) {
        String URL = "http://localhost:8080/sensors/check?name=" + uncheckedSensor;
        return Boolean.parseBoolean(restTemplate.getForObject(URL, String.class));
    }

    public void sending(String dataType, String URL) {
        dataOutsideMap.put(dataType, dataList);
        HttpEntity<Map<String, List<Map<String, String>>>> request = new HttpEntity<>(dataOutsideMap);
//        System.out.println(request); // For testing
        String measurementResponse = restTemplate.postForObject(URL, request, String.class);
        System.out.println(measurementResponse);
        dataList.clear();
        dataOutsideMap.clear();
    }
}
