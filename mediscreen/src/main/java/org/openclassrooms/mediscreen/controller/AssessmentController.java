package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.service.AssessmentService;
import org.openclassrooms.mediscreen.service.NoteService;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AssessmentController {

    private AssessmentService assessmentService;
    private PatientService patientService;
    private NoteService noteService;

    public AssessmentController(AssessmentService assessmentService, PatientService patientService, NoteService noteService) {
        this.assessmentService = assessmentService;
        this.patientService = patientService;
        this.noteService = noteService;
    }

    @GetMapping("/assess")
    public String showAssessment(@RequestParam Long id, Model model) {
        log.info("ASSESSING PATIENT id:{}", id);
        model.addAttribute("note", noteService.read(id));
        model.addAttribute("patient", patientService.read(id));
        return "assessment";
    }

    //TODO get correct path
    @PostMapping("/assess/patient")
    public String submitAssessment(Long patId, String familyName) {
        log.info("ASSESSING PATIENT");

        HealthAssessment assessment = assessmentService.assessPatient(patId, familyName);
        return "assessment";
    }

}
