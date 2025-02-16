package org.openclassrooms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openclassrooms.mediscreen.model.Patient;
import org.openclassrooms.mediscreen.repository.PatientRepository;
import org.openclassrooms.mediscreen.service.PatientService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Test
    void addOrUpdate() {
        Patient patient = new Patient();
        PatientRepository patientRepository = mock(PatientRepository.class);
        PatientService patientService = new PatientService(patientRepository);
        when(patientRepository.save(patient)).thenReturn(patient);
        patientService.addOrUpdate(patient);
        verify(patientRepository).save(patient);
    }

    @Test
    void read() {
        Patient patient = new Patient();
        PatientRepository patientRepository = mock(PatientRepository.class);
        PatientService patientService = new PatientService(patientRepository);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Patient readPatient = patientService.read(1L);
        Assertions.assertNotNull(readPatient);

        //negative
        readPatient = patientService.read(null);
        Assertions.assertNull(readPatient);
    }

    @Test
    void readFamily() {
        Patient patient = new Patient();
        PatientRepository patientRepository = mock(PatientRepository.class);
        PatientService patientService = new PatientService(patientRepository);
        when(patientRepository.findByFamily("lastname")).thenReturn(Optional.of(patient));
        Patient readPatient = patientService.readFamily("lastname");
        Assertions.assertNotNull(readPatient);

        //negative
        readPatient = patientService.readFamily(null);
        Assertions.assertNull(readPatient);
    }

    @Test
    void readAll() {
        List<Patient> patients = new ArrayList<>(Arrays.asList(new Patient(), new Patient()));
        PatientRepository patientRepository = mock(PatientRepository.class);
        PatientService patientService = new PatientService(patientRepository);
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> readPatients = patientService.readAll();
        Assertions.assertNotNull(readPatients);
        Assertions.assertEquals(2, patients.size());
    }

    @Test
    void delete() {
        Patient patient = new Patient();
        PatientRepository patientRepository = mock(PatientRepository.class);
        PatientService patientService = new PatientService(patientRepository);
        doNothing().when(patientRepository).delete(patient);
        patientService.delete(patient);

        verify(patientRepository).delete(patient);
    }
}
