package com.airtone.IsItRainingRestApiWebClient.controllers;

import com.airtone.IsItRainingRestApiWebClient.DataSending;
import com.airtone.IsItRainingRestApiWebClient.Service;
import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return "sensor/new";
    }
    @GetMapping("/measurement/new")
    public String addMeasurement(@ModelAttribute("sensor") Sensor sensor, Model model, @ModelAttribute("measurement") Measurement measurement, BindingResult bindingResult) {
        model.addAttribute("allSensors", service.showAllSensors());
        model.addAttribute("values", service.values());
        return "measurement/new";
    }
    @PostMapping("/measurement")
    public String createMeasurement(@ModelAttribute("sensor") Sensor sensor, Model model, @ModelAttribute("measurement") @Valid Measurement measurement, BindingResult bindingResult) {
//        if(dataSending.sensorCheck(measurement.getSensor()) == false)
//            bindingResult.rejectValue("sensor", "","A sensor with this name is not registered");

        if(bindingResult.hasErrors())
            return "measurement/new";
        service.newMeasurementAdding(measurement.getValue(), measurement.getRaining(), sensor.getName());
        return "redirect:/client/measurement/new";
    }
}
