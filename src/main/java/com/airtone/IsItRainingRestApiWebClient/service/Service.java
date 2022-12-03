package com.airtone.IsItRainingRestApiWebClient.service;

import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class Service {
    @Value("${tMin}")
    private int tMin;
    @Value("${tMax}")
    private int tMax;

    @Value("${batchSize}")
    private int batchSize;

    private final PostRequest postRequest;
    private final GetRequest getRequest;
    private final DeleteRequest deleteRequest;

    @Autowired
    public Service(PostRequest postRequest, GetRequest getRequest, DeleteRequest deleteRequest) {
        this.postRequest = postRequest;
        this.getRequest = getRequest;
        this.deleteRequest = deleteRequest;
    }


    //////////////////////// New Sensor Registration //////////////////////////////

    public void newSensorRegistration(String name) {
        Map<String, String> sensorInsideMap = new HashMap<>();
        sensorInsideMap.put("name", name);
        postRequest.dataList.add(sensorInsideMap);
        postRequest.sensorSending();
    }


    //////////////////////// Batch Sensor Registration ////////////////////////////

    public void batchSensorRegistration() {

        for(int i = 1; i<=batchSize; i++) {
            Map<String, String> sensorInsideMap = new HashMap<>();
            sensorInsideMap.put("name", "sensor_" + Integer.toString(i));
            postRequest.dataList.add(sensorInsideMap);
        }
        postRequest.sensorSending();
    }


    /////////////////////////// New Measurement adding ///////////////////////////

    public void newMeasurementAdding(String value, String raining, String sensor) {

        Map<String, String> measurementInsideMap = new HashMap<>();

        measurementInsideMap.put("value", value);
        measurementInsideMap.put("raining", raining);
        measurementInsideMap.put("sensor", sensor);

        postRequest.dataList.add(measurementInsideMap);
        postRequest.measurementSending();
    }

    /////////////////////////// Batch Measurement sending ///////////////////////////

    public void batchMeasurementSending() {

        for(int i = 1; i<=batchSize; i++) {
            Map<String, String> measurementBatchInsideMap = new HashMap<>();
            boolean rainingRandom = new Random().nextBoolean();
            double value = Math.random() * 200 - 100;
            value = Math.floor(100 * value) / 100;

            measurementBatchInsideMap.put("value", Double.toString(value));
            measurementBatchInsideMap.put("raining", Boolean.toString(rainingRandom));
            measurementBatchInsideMap.put("sensor", "sensor_" + Integer.toString(i));

            postRequest.dataList.add(measurementBatchInsideMap);
        }
        postRequest.measurementSending();
    }


    /////////////////////////// Show All Measurements /////////////////////////////

    public List<Measurement> showAllMeasurements() {
//        String showAllMeasurementsUrl = "http://localhost:8080/measurements";
//        AllMeasurements showAllMeasurementsResponse = restTemplate.getForObject(showAllMeasurementsUrl, AllMeasurements.class);
//        for(Measurement m : showAllMeasurementsResponse.getMeasurement()) {
//            System.out.println("measurement: raining: " + m.getRaining()
//                    + " temperature: " + m.getValue() + " sensor: " + m.getSensor());
//        }
//        return showAllMeasurementsResponse.getMeasurement();
        return getRequest.showAllMeasurements();
    }

    /////////////////////////// Show All Sensors /////////////////////////////

    public List<Sensor> showAllSensors() {
//        String showAllSensorsUrl = "http://localhost:8080/sensors";
//        AllSensors showAllSensorsResponse = restTemplate.getForObject(showAllSensorsUrl, AllSensors.class);
//        for(Sensor m : showAllSensorsResponse.getSensor()) {
//            System.out.println("measurement: name: " + m);
//        }
//        return showAllSensorsResponse.getSensor();
        return getRequest.showAllSensors();
    }


    /////////////////////////// Show All Rainy Days ///////////////////////////////

    public String showAllRainyDays() {
//        String allRainyDaysUrl = "http://localhost:8080/measurements/rainyDaysCount";
//        String allRainyDaysResponse = restTemplate.getForObject(allRainyDaysUrl,String.class);
//        return allRainyDaysResponse;
        return getRequest.showAllRainyDays();
    }


    ///////////////////////////  ClearAllData /////////////////////////////////////

    public void clearAllData() {
//        String clearAllDataUrl = "http://localhost:8080/sensors/clear";
//        restTemplate.delete(clearAllDataUrl, String.class);
        deleteRequest.clearAllData();
    }

    ///////////////////////////  Temperature values ///////////////////////////////

    public List<String> values() {
        List<String> values = new ArrayList<>();
        for(int i = tMax; i >= tMin; i--) {

            values.add(Integer.toString(i));
        }
        return values;
    }

    ///////////////////////  Checking a name of a sensor ///////////////////////////

    public boolean sensorCheck(String uncheckedSensor) {
        return postRequest.sensorCheck(uncheckedSensor);
    }
}
