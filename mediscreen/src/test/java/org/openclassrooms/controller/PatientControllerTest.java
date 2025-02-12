package org.openclassrooms.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.constants.GlobalConstants;
import org.openclassrooms.mediscreen.controller.PatientController;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class PatientControllerTest {

    @Test
    void showPatientForm() {
        Model model = mock(Model.class);
        PatientService patientService = mock(PatientService.class);
        PatientController patientController = new PatientController(patientService);

        String value = patientController.showPatientForm(1L, model);
        Assertions.assertEquals("patientForm", value);
    }

    @Test
    void submitPatientForm() {
        Patient patient = new Patient(1L, "family",
                "given", "dob", 'M',
                "address", "phone");

        BindingResult bindingResult = mock(BindingResult.class);
        PatientService patientService = mock(PatientService.class);
        PatientController patientController = new PatientController(patientService);

        //positive case
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(patientService).addOrUpdate(patient);
        when(patientService.readAll()).thenReturn(new ArrayList<>());
        String value = patientController.submitPatientForm(patient, bindingResult);
        Assertions.assertEquals(GlobalConstants.HOME, value);

        //errors forms
        when(bindingResult.hasErrors()).thenReturn(true);
        value = patientController.submitPatientForm(patient, bindingResult);
        Assertions.assertEquals("patientForm", value);
    }

    @Test
    void deletePatient() {
        PatientService patientService = mock(PatientService.class);
        PatientController patientController = new PatientController(patientService);

        when(patientService.read(1L)).thenReturn(new Patient());
        doNothing().when(patientService).delete(any(Patient.class));
        String value = patientController.deletePatient(1L);
        Assertions.assertEquals(GlobalConstants.HOME, value);
    }
}
