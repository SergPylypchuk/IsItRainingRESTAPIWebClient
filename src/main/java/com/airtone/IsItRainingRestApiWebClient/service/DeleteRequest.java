package com.airtone.IsItRainingRestApiWebClient.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeleteRequest {
    RestTemplate restTemplate = new RestTemplate();

    ///////////////////////////  ClearAllData /////////////////////////////////////

    public void clearAllData() {
        String clearAllDataUrl = "http://localhost:8080/sensors/clear";
        restTemplate.delete(clearAllDataUrl, String.class);
    }
}
