package org.openclassrooms.mediscreen.repository;

import org.openclassrooms.mediscreen.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    //TODO add querying

}
