package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientService {

    public PatientService() {
    }

    public void addPatient(Patient patient) {
        log.info("ADDING NEW PATIENT TO DATABASE");
    }
}
