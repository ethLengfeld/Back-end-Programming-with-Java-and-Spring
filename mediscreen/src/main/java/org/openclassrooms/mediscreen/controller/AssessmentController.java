package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.AssessmentService;
import org.openclassrooms.mediscreen.service.NoteService;
import org.openclassrooms.mediscreen.service.PatientService;
import org.openclassrooms.mediscreen.util.PatientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AssessmentController {

    private AssessmentService assessmentService;
    private PatientService patientService;
    private NoteService noteService;

    public AssessmentController(AssessmentService assessmentService,
                                PatientService patientService,
                                NoteService noteService) {
        this.assessmentService = assessmentService;
        this.patientService = patientService;
        this.noteService = noteService;
    }

    @GetMapping("/assess/patient")
    public String showAssessment(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String familyName,
                                 Model model) {
        log.info("ASSESSING PATIENT id:{} familyName:{}", id, familyName);
        Patient patient = null;
        Note note = null;
        if (id != null) {
            log.info("RETRIEVING NOTE & PATIENT BY id");
            patient = patientService.read(id);
            note = noteService.read(id);
        }
        else {
            log.info("RETRIEVING NOTE & PATIENT BY familyName");
            //TODO add query for selecting by familyName
//            patient = patientService.read(familyName);
            note = noteService.read(patient.getId());
        }

        HealthAssessment healthAssessment = assessmentService.assessPatient(patient);
        model.addAttribute("note", note);
        model.addAttribute("healthAssessment", healthAssessment.getValue());
        model.addAttribute("age", PatientUtils.calculateAge(patient.getDob()));

        return "assessment";
    }

}
