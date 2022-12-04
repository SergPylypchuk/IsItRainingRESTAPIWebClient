package com.airtone.IsItRainingRestApiWebClient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeleteRequest {
    @Value("${clearAllDataUrl}")
    private String clearAllDataUrl;
    RestTemplate restTemplate = new RestTemplate();

    ///////////////////////////  ClearAllData /////////////////////////////////////

    public void clearAllData() {
        restTemplate.delete(clearAllDataUrl, String.class);
    }
}
