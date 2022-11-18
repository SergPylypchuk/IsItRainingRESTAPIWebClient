package com.airtone.IsItRainingRestApiWebClient.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Measurement {
//    @NotEmpty(message = "Value should not be empty")
//    @Pattern(regexp = "^-?((100)(\\.0{1,2})?||(\\d{1,2})(\\.\\d{1,2})?)$"
//            , message = "Value should be between -100 and 100")
    private String value;
//    @NotEmpty(message = "Raining should not be empty")
//    @Pattern(regexp = "(true)|(false)", message = "Raining should be true or false")
    private String raining;
//    @NotEmpty(message = "Value should not be empty")
    private String sensor;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRaining() {
        return raining;
    }

    public void setRaining(String raining) {
        this.raining = raining;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

}
