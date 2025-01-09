package org.openclassrooms.mediscreen.service;

import lombok.Getter;
import org.openclassrooms.mediscreen.model.Patient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientService {

    @Getter
    private List<Patient> patientList;

    public PatientService() {
        patientList = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }
}
