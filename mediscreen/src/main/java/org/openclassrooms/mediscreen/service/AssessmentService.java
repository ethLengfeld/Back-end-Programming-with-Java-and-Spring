package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.util.PatientUtils;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class AssessmentService {

    private PatientService patientService;
    private NoteService noteService;

    public AssessmentService(PatientService patientService, NoteService noteService) {
        this.patientService = patientService;
        this.noteService = noteService;
    }

    public HealthAssessment assessPatient(Patient patient) {

        Note patientNote = noteService.read(patient.getId());

        int age = PatientUtils.calculateAge(patient.getDob());

        int countIndicators = 0;
        for (String note : patientNote.getDoctorNotes()) {
            //TODO fix logic
//            GlobalConstants.HEALTH_ASSESSMENT_WORDS
            if (note.contains("TODO")) {
                countIndicators++;
            }
        }

        return HealthAssessment.NONE;
    }
}
