package org.openclassrooms.mediscreen.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class PatientController {

    private final PatientService patientService;
    @Getter
    private List<Patient> patientList;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        patientList = patientService.readPatients();
    }

    @GetMapping("/")
    public String home(Model model) {
        log.info("AT INDEX PAGE");
        model.addAttribute("patients", patientList);
        return "index";
    }

    @GetMapping("/patient")
    public String showPatientForm(@RequestParam(required = false) Long id, Model model) {
        log.info("PATIENT FORM PAGE");
        log.info("id: {}", id);
        Patient patient = patientService.readPatient(id);

        if (patient == null) {
            model.addAttribute("patient", new Patient());
        }
        else {
            model.addAttribute("patient", patient);
        }

        return "patientForm";
    }

    @PostMapping("/patient/add")
    public String submitPatientForm(@Valid Patient patient, BindingResult bindingResult) {
        log.info("SUBMITTING PATIENT");
        if (bindingResult.hasErrors()) {
            return "patientForm";
        }
        patientService.addOrUpdatePatient(patient);
        patientList = patientService.readPatients();
        return "redirect:/";
    }

    @PostMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        log.info("DELETING PATIENT");
        patientService.deletePatient(patientService.readPatient(id));
        patientList = patientService.readPatients();
        return "redirect:/";
    }
}
