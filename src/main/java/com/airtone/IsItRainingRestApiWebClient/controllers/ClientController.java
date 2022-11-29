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

    @GetMapping()
    public String homePage() {
        return "index";
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
        return "redirect:/client/sensor/new";
    }
    @GetMapping("/measurement/new")
    public String addMeasurement(@ModelAttribute("sensor") Sensor sensor, Model model, @ModelAttribute("measurement") Measurement measurement, BindingResult bindingResult) {
        model.addAttribute("allSensors", service.showAllSensors());
        model.addAttribute("values", service.values());
        return "measurement/new";
    }
    @PostMapping("/measurement")
    public String createMeasurement(@ModelAttribute("sensor") Sensor sensor, @ModelAttribute("measurement") @Valid Measurement measurement, BindingResult bindingResult) {

        // Checking the sensor for manual data entry
//        if(dataSending.sensorCheck(measurement.getSensor()) == false)
//            bindingResult.rejectValue("sensor", "","A sensor with this name is not registered");

        if(bindingResult.hasErrors())
            return "measurement/new";
        service.newMeasurementAdding(measurement.getValue(), measurement.getRaining(), sensor.getName());
        return "redirect:/client/measurement/new";
    }

    @GetMapping("/sensor/batch/new")
    public String batchSensorGen() {
        return "sensor/batch";
    }
    @PostMapping("/sensor/batch")
    public String batchSensorCreate() {
        service.clearAllData();
        service.batchSensorRegistration();
        return "redirect:/client/measurement/batch/new";
    }

    @GetMapping("/measurement/batch/new")
    public String batchMeasurementGen() {
        return "measurement/batch";
    }
    @PostMapping("/measurement/batch")
    public String batchMeasurementCreate() {
        service.batchMeasurementSending();
        return "redirect:/client/measurement/show";
    }

    @GetMapping("/measurement/show")
    public String ShowAllMeasurements(Model model) {
        model.addAttribute("allMeasurements", service.showAllMeasurements());
        model.addAttribute("allMeasToStr",service.showAllMeasurements().toString());
        return "measurement/show";
    }

    @GetMapping("/measurement/rainydays")
    public String rainyDaysCounter(Model model) {
        model.addAttribute("rainyDays", service.showAllRainyDays());
        return "measurement/rainydays";
    }

    @GetMapping("/clear")
    public String clearDataPage() {
        return "clear";
    }
    @PostMapping("/clear")
    public String clearData() {
        service.clearAllData();
        return "redirect:/client";
    }



}
