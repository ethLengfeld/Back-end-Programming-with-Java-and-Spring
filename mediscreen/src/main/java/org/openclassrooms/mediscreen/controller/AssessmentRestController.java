package org.openclassrooms.mediscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.AssessmentService;
import org.openclassrooms.mediscreen.service.NoteService;
import org.openclassrooms.mediscreen.service.PatientService;
import org.openclassrooms.mediscreen.util.PatientUtils;
import org.openclassrooms.mediscreen.util.TemplateUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AssessmentRestController {

    private AssessmentService assessmentService;
    private PatientService patientService;
    private NoteService noteService;

    public AssessmentRestController(AssessmentService assessmentService,
                                    PatientService patientService,
                                    NoteService noteService) {
        this.assessmentService = assessmentService;
        this.patientService = patientService;
        this.noteService = noteService;
    }

    @GetMapping("/assess/patient")
    public String getPatientAssessment(@RequestParam(required = false) Long id,
                                       @RequestParam(required = false) String familyName) {
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
            patient = patientService.readFamily(familyName);
            note = noteService.read(patient.getId());
        }

        HealthAssessment patientAssessment = assessmentService.assessPatient(patient, note);
        log.info("PATIENT ASSESSMENT - {}", patientAssessment.getValue());
        int age = PatientUtils.calculateAge(patient.getDob());

        return "Patient: " + patient.getGiven() + " " + patient.getFamily() +
                "(age " + age + ") diabetes assessment is: " + patientAssessment.getValue();
    }

}
