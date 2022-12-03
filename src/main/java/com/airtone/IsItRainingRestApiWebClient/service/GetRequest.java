package com.airtone.IsItRainingRestApiWebClient.service;

import com.airtone.IsItRainingRestApiWebClient.model.AllMeasurements;
import com.airtone.IsItRainingRestApiWebClient.model.AllSensors;
import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GetRequest {
    RestTemplate restTemplate = new RestTemplate();


    /////////////////////////// Show All Measurements /////////////////////////////

    public List<Measurement> showAllMeasurements() {
        String showAllMeasurementsUrl = "http://localhost:8080/measurements";
        AllMeasurements showAllMeasurementsResponse = restTemplate.getForObject(showAllMeasurementsUrl, AllMeasurements.class);
        for(Measurement m : showAllMeasurementsResponse.getMeasurement()) {
            System.out.println("measurement: raining: " + m.getRaining()
                    + " temperature: " + m.getValue() + " sensor: " + m.getSensor());
        }
        return showAllMeasurementsResponse.getMeasurement();
    }

    /////////////////////////// Show All Sensors /////////////////////////////

    public List<Sensor> showAllSensors() {
        String showAllSensorsUrl = "http://localhost:8080/sensors";
        AllSensors showAllSensorsResponse = restTemplate.getForObject(showAllSensorsUrl, AllSensors.class);
        for(Sensor m : showAllSensorsResponse.getSensor()) {
            System.out.println("measurement: name: " + m);
        }
        return showAllSensorsResponse.getSensor();
    }


    /////////////////////////// Show All Rainy Days ///////////////////////////////

    public String showAllRainyDays() {
        String allRainyDaysUrl = "http://localhost:8080/measurements/rainyDaysCount";
        String allRainyDaysResponse = restTemplate.getForObject(allRainyDaysUrl,String.class);
        return allRainyDaysResponse;
    }
}
