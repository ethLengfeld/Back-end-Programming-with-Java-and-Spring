package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient) {
        log.info("ADDING NEW PATIENT TO DATABASE");
        patientRepository.save(patient);
    }

    public List<Patient> readPatients() {
        log.info("READING ALL PATIENTS FROM DATABASE");
        return patientRepository.findAll();
    }
}
