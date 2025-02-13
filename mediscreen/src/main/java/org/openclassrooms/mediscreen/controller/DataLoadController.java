package org.openclassrooms.mediscreen.controller;

import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoadController {

    PatientService patientService;

    public DataLoadController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/load/patient/info")
    public String loadPatientInfo(String family) {
        return patientService.readFamily(family).getId().toString();
    }
}
