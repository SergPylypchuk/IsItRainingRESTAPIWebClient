package com.airtone.IsItRainingRestApiWebClient.controllers;

import com.airtone.IsItRainingRestApiWebClient.DataSending;
import com.airtone.IsItRainingRestApiWebClient.Service;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final Service service;
    private final DataSending dataSending;

    @Autowired
    public ClientController(Service service, DataSending dataSending) {
        this.service = service;
        this.dataSending = dataSending;
    }

    @GetMapping("/sensor/new")
    public String newSensorReg(@ModelAttribute ("sensor") Sensor sensor) {
        return "sensor/new";
    }
    @PostMapping("/sensor")
    public String sensorCreate(@ModelAttribute ("sensor") @Valid Sensor sensor, BindingResult bindingResult) {
        if(dataSending.sensorCheck(sensor.getName()) == true)
            bindingResult.rejectValue("name", "","A sensor with this name already registered");
        if(bindingResult.hasErrors())
            return "/sensor/new";
        service.newSensorRegistration(sensor.getName());
        System.out.println("PM test");
        return "sensor/new";
    }
}
