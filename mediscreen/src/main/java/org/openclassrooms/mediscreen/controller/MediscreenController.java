package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class MediscreenController {

    private final PatientService patientService;
    private List<Patient> patientList;

    public MediscreenController(PatientService patientService) {
        this.patientService = patientService;
        this.patientList = this.patientService.readAll();
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("AT INDEX PAGE");
        patientList = patientService.readAll();
        model.addAttribute("patients", patientList);
        return "index";
    }

}
