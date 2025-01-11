package org.openclassrooms.mediscreen.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class PatientController {

    private final PatientService patientService;
    @Getter
    private List<Patient> patientList;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        //TODO populate with call to db
        patientList = new ArrayList<>();
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("AT INDEX PAGE");
        model.addAttribute("patients", patientList);
        return "index";
    }

    @GetMapping("/patient")
    public String showPatientForm(Model model) {
        log.info("AT PATIENT FORM PAGE");
        //TODO future case check if patient already exists?
        model.addAttribute("patient", new Patient());
        return "patientForm";
    }

    @PostMapping("/patient/add")
    public String addPatient(Patient patient, Model model) {
        log.info("SUBMITTED PATIENT");
        patientList.add(patient);
        patientService.addPatient(patient);
        return "redirect:/";
    }
}
