package com.airtone.IsItRainingRestApiWebClient.service;

import com.airtone.IsItRainingRestApiWebClient.model.AllMeasurements;
import com.airtone.IsItRainingRestApiWebClient.model.AllSensors;
import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GetRequest {
    @Value("${showAllMeasurementsUrl}")
    private String showAllMeasurementsUrl;

    @Value("${showAllSensorsUrl}")
    private String showAllSensorsUrl;

    @Value("${allRainyDaysUrl}")
    private String allRainyDaysUrl;

    @Value("${sensorCheckUrl}")
    private String sensorCheckUrl;

    RestTemplate restTemplate = new RestTemplate();


    /////////////////////////// Show All Measurements /////////////////////////////

    public List<Measurement> showAllMeasurements() {
        AllMeasurements showAllMeasurementsResponse = restTemplate.getForObject(showAllMeasurementsUrl, AllMeasurements.class);
        assert showAllMeasurementsResponse != null;
    /////////////////////////////////// testing ///////////////////////////////////
//        for(Measurement m : showAllMeasurementsResponse.getMeasurement()) {
//            System.out.println("measurement: raining: " + m.getRaining()
//                    + " temperature: " + m.getValue() + " sensor: " + m.getSensor());
//        }
        return showAllMeasurementsResponse.getMeasurement();
    }

    /////////////////////////// Show All Sensors /////////////////////////////

    public List<Sensor> showAllSensors() {
        AllSensors showAllSensorsResponse = restTemplate.getForObject(showAllSensorsUrl, AllSensors.class);
        assert showAllSensorsResponse != null;
        /////////////////////////////////// testing ///////////////////////////////////
//        for(Sensor m : showAllSensorsResponse.getSensor()) {
//            System.out.println("measurement: name: " + m);
//        }
        return showAllSensorsResponse.getSensor();
    }


    /////////////////////////// Show All Rainy Days ///////////////////////////////

    public String showAllRainyDays() {
        return restTemplate.getForObject(allRainyDaysUrl, String.class);
    }

    public boolean sensorCheck(String uncheckedSensor) {
        return Boolean.parseBoolean(restTemplate.getForObject(sensorCheckUrl + uncheckedSensor, String.class));
    }
}
