package org.openclassrooms.service;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.AssessmentService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentServiceTest {

    @Test
    void assessPatient() {
        AssessmentService assessmentService = new AssessmentService();
        Patient patient = new Patient();
        patient.setSex('M');
        patient.setDob("1998-05-22");
        Note note = new Note();
        note.setDoctorNotes(new ArrayList<>());

        assertEquals(HealthAssessment.NONE, assessmentService.assessPatient(patient, note));
    }
}
