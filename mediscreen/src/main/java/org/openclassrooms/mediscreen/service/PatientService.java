package org.openclassrooms.mediscreen.service;

import lombok.extern.slf4j.Slf4j;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientService implements CrudService<Patient> {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void addOrUpdate(Patient patient) {
        log.info("SAVING PATIENT");
        patientRepository.save(patient);
    }

    @Override
    public Patient read(Long id) {
        log.info("READING PATIENT WITH id:::{}", id);
        if (id == null) {
            return null;
        }
        return patientRepository.findById(id).get();
    }

    @Override
    public List<Patient> readAll() {
        log.info("READING ALL PATIENTS");
        return patientRepository.findAll();
    }

    @Override
    public void delete(Patient patient) {
        log.info("DELETING PATIENT");
        patientRepository.delete(patient);
    }
}
