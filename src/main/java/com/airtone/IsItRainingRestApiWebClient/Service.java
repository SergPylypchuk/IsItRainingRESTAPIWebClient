package com.airtone.IsItRainingRestApiWebClient;

import com.airtone.IsItRainingRestApiWebClient.model.AllMeasurements;
import com.airtone.IsItRainingRestApiWebClient.model.AllSensors;
import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class Service {
    RestTemplate restTemplate = new RestTemplate();
    DataSending dataSending = new DataSending();


    //////////////////////// New Sensor Registration //////////////////////////////

    public void newSensorRegistration(String name) {
        Map<String, String> sensorInsideMap = new HashMap<>();
        sensorInsideMap.put("name", name);
        dataSending.dataList.add(sensorInsideMap);
        dataSending.sensorSending();
    }


    //////////////////////// Batch Sensor Registration ////////////////////////////

    public void batchSensorRegistration() {

        for(int i = 1; i<=1000; i++) {
            Map<String, String> sensorInsideMap = new HashMap<>();
            sensorInsideMap.put("name", "sensor_" + Integer.toString(i));
            dataSending.dataList.add(sensorInsideMap);
        }
        dataSending.sensorSending();
    }


    /////////////////////////// New Measurement adding ///////////////////////////

    public void newMeasurementAdding(String value, String raining, String sensor) {

        Map<String, String> measurementInsideMap = new HashMap<>();

        measurementInsideMap.put("value", value);
        measurementInsideMap.put("raining", raining);
        measurementInsideMap.put("sensor", sensor);

        dataSending.dataList.add(measurementInsideMap);
        dataSending.measurementSending();
    }

    /////////////////////////// Batch Measurement sending ///////////////////////////

    public void batchMeasurementSending() {

        for(int i = 1; i<=1000; i++) {
            Map<String, String> measurementBatchInsideMap = new HashMap<>();
            boolean rainingRandom = new Random().nextBoolean();
            double value = Math.random() * 200 - 100;
            value = Math.floor(100 * value) / 100;

            measurementBatchInsideMap.put("value", Double.toString(value));
            measurementBatchInsideMap.put("raining", Boolean.toString(rainingRandom));
            measurementBatchInsideMap.put("sensor", "sensor_" + Integer.toString(i));

            dataSending.dataList.add(measurementBatchInsideMap);
        }
        dataSending.measurementSending();
    }


    /////////////////////////// Show All Measurements /////////////////////////////

    public void showAllMeasurements() {
        String showAllMeasurementsUrl = "http://localhost:8080/measurements";
        AllMeasurements showAllMeasurementsResponse = restTemplate.getForObject(showAllMeasurementsUrl, AllMeasurements.class);
        for(Measurement m : showAllMeasurementsResponse.getMeasurement()) {
            System.out.println("measurement: raining: " + m.getRaining()
                    + " temperature: " + m.getValue() + " sensor: " + m.getSensor());
        }
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


    ///////////////////////////  ClearAllData /////////////////////////////////////

    public void clearAllData() {
        String clearAllDataUrl = "http://localhost:8080/sensors/clear";
        restTemplate.delete(clearAllDataUrl, String.class);
    }

    ///////////////////////////  Temperature values ///////////////////////////////
    public List<String> values() {
        List<String> values = new ArrayList<>();
        for(int i = -35; i<41; i++) {

            values.add(Integer.toString(i));
        }
        return values;
    }
}
