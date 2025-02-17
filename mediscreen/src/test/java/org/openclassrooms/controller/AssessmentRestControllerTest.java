package org.openclassrooms.controller;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.controller.AssessmentRestController;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.AssessmentService;
import org.openclassrooms.mediscreen.service.NoteService;
import org.openclassrooms.mediscreen.service.PatientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssessmentRestControllerTest {

    @Test
    void getPatientAssessment() {
        AssessmentService assessmentService = mock(AssessmentService.class);
        PatientService patientService = mock(PatientService.class);
        NoteService noteService = mock(NoteService.class);
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setGiven("Test");
        patient.setFamily("TestLast");
        patient.setDob("2020-01-01");
        Note note = new Note();
        note.setDoctorNotes(List.of("doctor note", "here's a new doctor note"));

        AssessmentRestController assessmentRestController = new AssessmentRestController(
                assessmentService, patientService, noteService);

        when(patientService.read(1L)).thenReturn(patient);
        when(noteService.read(1L)).thenReturn(note);
        when(patientService.readFamily("familyName")).thenReturn(patient);
        when(noteService.read(1L)).thenReturn(note);
        when(assessmentService.assessPatient(any(), any())).thenReturn(HealthAssessment.NONE);

        assertEquals("Patient: Test TestLast(age 5) diabetes assessment is: None",
                assessmentRestController.getPatientAssessment(1L, null));
        assertEquals("Patient: Test TestLast(age 5) diabetes assessment is: None",
                assessmentRestController.getPatientAssessment(null, "familyName"));
    }
}
