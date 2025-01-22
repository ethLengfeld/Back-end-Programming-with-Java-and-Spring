package org.openclassrooms.mediscreen.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class PatientController {

    private final PatientService patientService;
    @Getter
    private List<Patient> patientList;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        this.patientList = patientService.readAll();
    }

    @GetMapping("/patient")
    public String showPatientForm(@RequestParam(required = false) Long id, Model model) {
        log.info("PATIENT FORM PAGE");
        log.info("id: {}", id);
        Patient patient = patientService.read(id);

        model.addAttribute("patient", Objects.requireNonNullElseGet(patient, Patient::new));

        return "patientForm";
    }

    @PostMapping("/patient/add")
    public String submitPatientForm(@Valid Patient patient, BindingResult bindingResult) {
        log.info("SUBMITTING PATIENT");
        if (bindingResult.hasErrors()) {
            return "patientForm";
        }
        patientService.addOrUpdate(patient);
        patientList = patientService.readAll();
        return GlobalConstants.HOME;
    }

    @PostMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        log.info("DELETING PATIENT");
        patientService.delete(patientService.read(id));
        patientList = patientService.readAll();

        //TODO delete notes
        return GlobalConstants.HOME;
    }
}
