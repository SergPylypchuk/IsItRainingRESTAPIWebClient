package com.airtone.IsItRainingRestApiWebClient.controllers;

import com.airtone.IsItRainingRestApiWebClient.service.Service;
import com.airtone.IsItRainingRestApiWebClient.model.Measurement;
import com.airtone.IsItRainingRestApiWebClient.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final Service service;

    @Autowired
    public ClientController(Service service) {
        this.service = service;
    }


    //////////////////////////////////////// Home Page ////////////////////////////////////////////////
    @GetMapping()
    public String homePage() {
        return "index";
    }


    /////////////////////////////// Manual sensor registration ////////////////////////////////////////
    @GetMapping("/sensor/new")
    public String newSensorReg(@ModelAttribute ("sensor") Sensor sensor) {
        return "sensor/new";
    }

    @PostMapping("/sensor")
    public String sensorCreate(@ModelAttribute ("sensor") @Valid Sensor sensor, BindingResult bindingResult) {
        if(service.sensorCheck(sensor.getName()) == true)
            bindingResult.rejectValue("name", "",
                    "A sensor with this name already registered");
        if(bindingResult.hasErrors())
            return "/sensor/new";
        service.newSensorRegistration(sensor.getName());
        return "redirect:/client/sensor/new";
    }


    ////////////////////////////// Manual adding of a measurement ////////////////////////////////////
    @GetMapping("/measurement/new")
    public String addMeasurement(@ModelAttribute("sensor") Sensor sensor, Model model,
                                 @ModelAttribute("measurement") Measurement measurement, BindingResult bindingResult) {
        model.addAttribute("allSensors", service.showAllSensors());
        model.addAttribute("values", service.values());
        return "measurement/new";
    }

    @PostMapping("/measurement")
    public String createMeasurement(@ModelAttribute("sensor") Sensor sensor, Model model,
                                    @ModelAttribute("measurement") @Valid Measurement measurement,
                                    BindingResult bindingResult) {

        // Checking the sensor for manual data typing
//        if(service.sensorCheck(measurement.getSensor()) == false)
//            bindingResult.rejectValue("sensor", "","You should set all fields");

        if(sensor.getName() == null) {
            System.out.println(measurement.getValue() + '\n' + measurement.getRaining() + '\n' + sensor.getName());
            bindingResult.rejectValue("sensor", "", "This field should not be empty");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("allSensors", service.showAllSensors());
            model.addAttribute("values", service.values());
            return "measurement/new";
        }

        service.newMeasurementAdding(measurement.getValue(), measurement.getRaining(), sensor.getName());
        return "redirect:/client/measurement/new";
    }

    ////////////////////////////// Batch mode - registration of a batch of sensors ///////////////////////////////////
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

    ////////////////////////////// Batch mode - adding of a batch of measurements ////////////////////////////////////
    @GetMapping("/measurement/batch/new")
    public String batchMeasurementGen() {
        return "measurement/batch";
    }
    @PostMapping("/measurement/batch")
    public String batchMeasurementCreate() {
        service.batchMeasurementSending();
        return "redirect:/client/measurement/show";
    }

    //////////////////////////////////////////// Show all measurements ///////////////////////////////////////////////
    @GetMapping("/measurement/show")
    public String ShowAllMeasurements(Model model) {
        model.addAttribute("allMeasurements", service.showAllMeasurements());
        model.addAttribute("allMeasToStr",service.showAllMeasurements().toString());
        return "measurement/show";
    }

    /////////////////////////////////////////// Show rainy days counter //////////////////////////////////////////////
    @GetMapping("/measurement/rainydays")
    public String rainyDaysCounter(Model model) {
        model.addAttribute("rainyDays", service.showAllRainyDays());
        return "measurement/rainydays";
    }

    ///////////////////////////////////////////////// Clear all data /////////////////////////////////////////////////
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
