package com.airtone.IsItRainingRestApiWebClient.model;

import java.util.List;

public class AllSensors {
    private List<Sensor> sensor;

    public List<Sensor> getSensor() {
        return sensor;
    }

    public void setSensor(List<Sensor> sensor) {
        this.sensor = sensor;
    }
}
