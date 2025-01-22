package org.openclassrooms.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.controller.MediscreenController;
import org.openclassrooms.mediscreen.service.PatientService;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MediscreenControllerTest {

    @Test
    void home() {
        PatientService patientService = mock(PatientService.class);
        Model model = mock(Model.class);
        MediscreenController mediscreenController = new MediscreenController(patientService);
        when(patientService.readAll()).thenReturn(new ArrayList<>());
        String value = mediscreenController.home(model);
        Assertions.assertEquals("index", value);
    }
}
