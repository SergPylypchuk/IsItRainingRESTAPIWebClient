package com.airtone.IsItRainingRestApiWebClient;

import java.util.List;

public class FindAllResponse {
    private List<Measurement> measurement;

    public List<Measurement> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(List<Measurement> measurement) {
        this.measurement = measurement;
    }

}
