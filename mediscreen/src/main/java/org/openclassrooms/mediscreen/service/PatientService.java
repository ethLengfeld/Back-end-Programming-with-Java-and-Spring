package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addOrUpdatePatient(Patient patient) {
        log.info("SAVING PATIENT");
        patientRepository.save(patient);
    }

    public Patient readPatient(Long id) {
        log.info("READING PATIENT WITH id:::{}", id);
        if (id == null) {
            return null;
        }
        return patientRepository.findById(id).get();
    }

    public List<Patient> readPatients() {
        log.info("READING ALL PATIENTS");
        return patientRepository.findAll();
    }

    public void deletePatient(Patient patient) {
        log.info("DELETING PATIENT");
        patientRepository.delete(patient);
    }
}
