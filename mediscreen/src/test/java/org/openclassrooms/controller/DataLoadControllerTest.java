package org.openclassrooms.controller;

import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.controller.DataLoadController;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataLoadControllerTest {

    @Test
    void loadPatientInfo() {
        Patient patient = new Patient();
        patient.setId(1L);
        PatientService patientService = mock(PatientService.class);
        DataLoadController dataLoadController = new DataLoadController(patientService);

        when(patientService.readFamily("lastName")).thenReturn(patient);

        assertEquals("1", dataLoadController.loadPatientInfo("lastName"));
    }
}
