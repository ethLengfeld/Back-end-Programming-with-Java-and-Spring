package org.openclassrooms.controller;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.HealthAssessment;
import org.openclassrooms.mediscreen.controller.AssessmentController;
import org.openclassrooms.mediscreen.model.Note;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.AssessmentService;
import org.openclassrooms.mediscreen.service.NoteService;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssessmentControllerTest {

    @Test
    void showAssessment() {
        Model model = mock(Model.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PatientService patientService = mock(PatientService.class);
        NoteService noteService = mock(NoteService.class);
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setDob("2020-01-01");
        Note note = new Note();
        note.setDoctorNotes(List.of("doctor note", "here's a new doctor note"));

        AssessmentController assessmentController = new AssessmentController(
                assessmentService, patientService, noteService);

        when(patientService.read(1L)).thenReturn(patient);
        when(noteService.read(1L)).thenReturn(note);
        when(patientService.readFamily("familyName")).thenReturn(patient);
        when(noteService.read(1L)).thenReturn(note);
        when(assessmentService.assessPatient(any(), any())).thenReturn(HealthAssessment.NONE);

        assertEquals("assessment", assessmentController.showAssessment(1L, null, model));
        assertEquals("assessment", assessmentController.showAssessment(null, "familyName", model));
    }
}
