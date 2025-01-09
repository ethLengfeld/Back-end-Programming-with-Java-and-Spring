package org.openclassrooms.mediscreen.controller;

import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("patients", patientService.getPatientList());
        return "index";
    }

    @GetMapping("/patient")
    public String showPatientForm(Model model) {
        //TODO future case check if patient already exists?
        model.addAttribute("patient", new Patient());
        return "patientForm";
    }

    @PostMapping("/patient/add")
    public String addPatient(Patient patient, Model model) {
        patientService.addPatient(patient);
//        curl -d "family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333" -X POST http://localhost:8081/patient/add
        /* data required
            family name - String (Last name)
            given name - String (First name)
            dob - String YYYY-MM-dd
            sex - char M/F (radio button)
            address - String
            phone - String
         */
        return "redirect:/";
    }
}
